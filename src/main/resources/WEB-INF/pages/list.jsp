<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en" dir="ltr">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <title>Prog.kiev.ua</title>
  </head>
  <body>
  <br>
  <div class="container">
    <div class="row justify-content-md-center">
    <form action="/listDelete" method="POST">
      <table class="table table-bordered" max-width="300px">
        <thead class="thead-light">
        <tr>
          <th>id</th>
          <th>name</th>
          <th>del</th>
        </tr>
        <tbody>
        <c:forEach var="photo" items="${photos}">
        <tr>
          <td><c:out value="${photo.key}"/></td>
          <td><c:out value="${photo.value.originalFilename}"/></td>
          <td><input type="checkbox" name="${photo.value.originalFilename}" value="${photo.key}"/></td>
        </tr>
      </c:forEach>
        </tbody>
      </table>
      <input type="submit" />
    </form>
    </div>
  </div>
  </body>
</html>
