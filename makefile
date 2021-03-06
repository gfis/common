#!/usr/bin/make

# Test Common functions and other utility targets
# @(#) $Id: 2bbd9511422674a354fe5a19f2d55437adbebce0 $
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
	grep -E '> FAILED' $(TESTDIR)/regression*.log | cut -f 3 -d ' ' | xargs -l -ißß echo rm -v test/ßß.prev.tst
recr1:
	grep -E '> FAILED' $(TESTDIR)/regression*.log | cut -f 3 -d ' ' | xargs -l -ißß rm -v test/ßß.prev.tst
regr2:
	make regression TEST=$(TEST) > x.tmp
#--------------------------------------------------
# test whether all defined tests in all.tests have *.prev.tst results and vice versa
check_tests:
	grep -E "^TEST" $(TESTDIR)/all.tests | cut -b 6-8 | sort | uniq -c > $(TESTDIR)/tests_formal.tmp
	ls -1 $(TESTDIR)/*.prev.tst          | cut -b 6-8 | sort | uniq -c > $(TESTDIR)/tests_actual.tmp
	diff -y --suppress-common-lines --width=32 $(TESTDIR)/tests_formal.tmp $(TESTDIR)/tests_actual.tmp
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
	