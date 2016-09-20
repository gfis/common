<?xml version="1.0" encoding="UTF-8"?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="application/xhtml+xml;charset=UTF-8" />
<meta name="robots" content="noindex, nofollow" />
<link rel="stylesheet" type="text/css" href="stylesheet.css" />
<title>Common Main Page</title>
</head>
<body>
<h3>Common Version Vn.vvvv/yyyy-mm-dd<h3>
<p>This project collects a series of classes and methods which are useful in several subprojects.</p>
<h3>Parameter Test</h3>
<form action="servlet" method="POST" enctype="multipart/form-data">
<input name="view" type="hidden" value="index" />
<h4>Form Fields</h4>
[field0] param1:  <input name="param1" type="text" size="16" value="value1" /><br />
[field1] param2:  <input name="param2" type="text" size="16" value="value2" /><br />
[field2] parm1:  <input name="parm1" type="text" size="16" value="Georg" /><br />
[field3] parm2:  <input name="parm2" type="text" size="16" value="2906" /><br />
[field4] view:  <input name="view" type="text" size="16" value="index" /><br />
<h4>Form Files</h4>
[file0]<em> regression.properties</em>:  <input name="regression.properties" type="file" size="16"" style="font-family: Courier, monospace"/><br />
[file1]<em> file1</em>:  <input name="file1" type="file" size="16"" style="font-family: Courier, monospace"/><br />
  <input type="submit" value="Submit"><br />
</form>
<h4>Content of File 0:<em>regression.properties</em></h4>
\-\-29061947[0-9a-z]+
\-\-29061947ccccccccccc
#------------------
 \d{4}\-\d{2}\-\d{2} \d{2}\:\d{2}\:\d{2}\.\d{1,3}
 yyyy-mm-dd hh:mm:ss.SSS
#------------------
 \d{4}\-\d{2}\-\d{2} \d{2}\:\d{2}\:\d{2}
 yyyy-mm-dd hh:mm:ss
#------------------
Common Version V\d+\.\d+\/\d{4}\-\d{2}\-\d{2}
Common Version Vn.vvvv/yyyy-mm-dd
#------------------
\$Id\: [0-9a-h]+ \$
$Id: githash $
#------------------

</pre>
<a title="wiki"        href="http://www.teherba.org/index.php/Common" target="_new">Wiki</a> Documentation<br />
<a title="github"      href="https://github.com/gfis/ramath" target="_new">Git Repository</a><br />
<a title="api"         href="docs/api/index.html">Java API</a> Documentation<br />
<a title="manifest"    href="servlet?view=manifest">Manifest</a>, <a title="license"     href="servlet?view=license">License</a>, <a title="notice"      href="servlet?view=notice">References</a><br />
<!-- language="en", features="quest" -->
<p><span style="font-size:small">
Questions, remarks: email to  <a href="mailto:punctum@punctum.com?&subject=Common">Dr. Georg Fischer</a></span></p>
</body></html>
