#!/usr/bin/make

# Test Common functions and other utility targets
# @(#) $Id: 2bbd9511422674a354fe5a19f2d55437adbebce0 $
# 2016-08-29: Georg Fischer: copied fro Dbat

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
# Perform a regression test (a complete run > 250 testcases with TEST=% takes > 17 s)
regression: 
	java -Dlog4j.debug -classpath "dist/common.jar;c:/var/lib/tomcat/openlib/log4j-1.2.17.jar" \
			org.teherba.common.RegressionTester $(TESTDIR)/common.tests $(TEST) 2>&1 \
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
# test whether all defined tests in common.tests have *.prev.tst results and vice versa
check_tests:
	grep -E "^TEST" $(TESTDIR)/common.tests | cut -b 6-8 | sort | uniq -c > $(TESTDIR)/tests_formal.tmp
	ls -1 $(TESTDIR)/*.prev.tst            | cut -b 6-8 | sort | uniq -c > $(TESTDIR)/tests_actual.tmp
	diff -y --suppress-common-lines --width=32 $(TESTDIR)/tests_formal.tmp $(TESTDIR)/tests_actual.tmp
#---------------------------------------------------
jfind:
	find src -iname "*.java" | xargs -l grep -H $(JF)
rmbak:
	find src -iname "*.bak"  | xargs -l rm -v
