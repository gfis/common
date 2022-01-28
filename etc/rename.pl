#!/usr/bin/perl

# rename some variables in place, create a .bak file
# @(#) $Id: rename.pl 113 2009-04-06 14:57:07Z gfis $
# 2022-01-27: for the log4j 1.x -> 2.17.1 conversion, with .bak; *VH
# 2009-04-06: Georg Fischer 
#-------------------------------------------------------

use strict;
use utf8;
use integer;

my $file = shift (@ARGV);
undef $/; # slurp mode
open (IN, "<", $file) || die "cannot read $file";
my $buffer = <IN>;
close(IN);
open (BAK, ">", "$file.bak") || die "cannot write $file.bak";
print BAK $buffer;
close(BAK);
# $buffer =~ s{org\.apache\.logging\.log4j\.Logger\;} {org\.apache\.logging\.log4j\.Logger\;\nimport  org\.apache\.logging\.log4j\.LogManager\;}g;
$buffer =~ s{org\.apache\.log4j\.Logger\;} {org\.apache\.logging\.log4j\.Logger\;\nimport  org\.apache\.logging\.log4j\.LogManager\;}g;
$buffer =~ s{Logger\.getLogger *\(}        {LogManager\.getLogger\(}g;
open (OUT, ">", $file) || die "cannot write $file";
print OUT $buffer;
close(OUT);
print STDERR "modified $file\n";
