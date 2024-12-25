#!/usr/bin/make

# Test Common functions and other utility targets
# @(#) $Id: 2bbd9511422674a354fe5a19f2d55437adbebce0 $
# 2024-12-25: nnnn
# 2022-01-28: log4j V1.x -> V2.17 migration targets
# 2016-08-29: Georg Fischer: copied from Dbat

DIFF=diff -y --suppress-common-lines --width=160
DIFF=diff -w -rs -C0
SRC=src/main/java/org/teherba/common
TOMC=/var/lib/tomcat/webapps/common
TOMC=c:/var/lib/tomcat/webapps/common
TESTDIR=test
# the following can be overriden outside for single or subset tests,
# for example make regression TEST=U%
TEST="%"
# for Windows, SUDO should be empty
SUDO=

all: regression
#-------------------------------------------------------------------
# Perform a regression test
regression: 
	java -Djdk.net.registerGopherProtocol=true -classpath "dist/common.jar" \
			org.teherba.common.RegressionTester $(TESTDIR)/all.tests $(TEST) 2>&1 \
	| tee $(TESTDIR)/regression.log
	grep FAILED $(TESTDIR)/regression.log

#	java -Dlog4j.debug 
#----
# Recreate all testcases which failed (i.e. remove xxx.prev.tst)
# Handle with care!
# Failing testcases are turned into "passed" and are manifested by this target!
recreate: recr1 regr2
recr0:
	grep -E '> FAILED' $(TESTDIR)/regression*.log | cut -f 3 -d ' ' | xargs -l -innnn echo rm -v test/nnnn.prev.tst
recr1:
	grep -E '> FAILED' $(TESTDIR)/regression*.log | cut -f 3 -d ' ' | xargs -l -innnn rm -v test/nnnn.prev.tst
regr2:
	make regression TEST=$(TEST) > x.tmp
#--------------------------------------------------
# test whether all defined tests in all.tests have *.prev.tst results and vice versa
check_tests:
	grep -E "^TEST" $(TESTDIR)/all.tests | cut -b 6-8 | sort | uniq -c > $(TESTDIR)/tests_formal.tmp
	ls -1 $(TESTDIR)/*.prev.tst          | cut -b 6-8 | sort | uniq -c > $(TESTDIR)/tests_actual.tmp
	diff -y --suppress-common-lines --width=32 $(TESTDIR)/tests_formal.tmp $(TESTDIR)/tests_actual.tmp
#----------------
# Targets for the log4j 1.x -> log4j 2.17 migration in an application directory $(DIR)
log4j: list4 rename4 config4 lib4 dist4 # DIR=
list4: # DIR= ; show the Java source files that are affected
	find ../$(DIR)/src/main/java -iname "*.java" | xargs -l grep -il "Logger" \
	| tee $@.$(DIR).tmp
rename4: # DIR= ; modify the affected java source files
	cat list4.$(DIR).tmp | xargs -l perl etc/rename.pl
config4: # DIR= ; copies the XML configuration and build-import.xml
	cp -v etc/log4j2.xml ../$(DIR)/etc
	cd ../$(DIR)/etc ; git add -v log4j2.xml ; mv -v log4j.properties log4j.properties.old
	cp -v build-import.xml ../$(DIR)
lib4: # DIR= ; copies the new libraries
	mv -v ../$(DIR)/lib/log4j-1.2.17.jar ../$(DIR)/lib/log4j-1.2.17.jar.old || :
	mkdir ../$(DIR)/lib || :
	cp -v lib/log4j-*-2.17.1.jar ../$(DIR)/lib || :
dist4: # DIR= ; compiles the package
	cd ../$(DIR) ; ant clean deploy
#---------------------------------------------------
jfind:
	find src -iname "*.java" | xargs -l grep -iH "$(JF)"
rmbak:
	find src -iname "*.bak"  | xargs -l rm -v
#---------------------------------------------------
multi0:
	java -cp dist/common.jar org.teherba.common.priv.URIMultiPart | tee multi0.tmp
	diff -C0 multi0.tmp ../gramword/test/sample.multipart.request.txt
multi1:
	java -cp dist/common.jar org.teherba.common.priv.URIMultiPart ../gramword/test/wachstube.txt \
			http://localhost:8080/gramword/servlet
#---------------------------------------------------
new: new1 new2
new1:
	rm -rf dummy
	ant deploy
new2:
	java -Duser.language=en -cp dist/common.jar org.teherba.common.StaticMirror  "http://localhost:8080/dbat/servlet?spec=migr/index" dummy
	find dummy -type f | wc -l
	