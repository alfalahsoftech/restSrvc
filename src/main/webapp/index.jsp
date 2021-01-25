<html>
<body>
    <h2>Jersey RESTful Web Application!</h2>
    <p><a href="webapi/myresource">Jersey resource</a>
    <p>Visit <a href="http://jersey.java.net">Project Jersey website</a>
    for more information on Jersey!
    
       <form action="rest/fileHandler/uploadFile" method="post" enctype="multipart/form-data">
            <p>Select a file : <input type="file" name="file" size="45"  /></p>
          <p>  <input type="checkbox" name="isNew">&nbsp;&nbsp;:Is New</p>
            <p> Destination: <input name="destDir"></p>
            <input type="submit" value="Upload PDF" />
            
        </form>
</body>
</html>
