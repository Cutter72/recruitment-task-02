<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pl-PL">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="X-UA-Compatible" content="ie=edge"/>
    <title>My favourite artists</title>
</head>
<body>
<a href="/">back</a>
<h1>My favourite artists</h1>
<table>
<tr>
    <th>Name</th>
    <th>Remove from favourites</th>
</tr>
<c:forEach items="${artists}" var="artist">
    <tr style="height: 50px">
        <td><a href="${artist.externalUrls.externalUrls.get('spotify')}" target="_blank">${artist.name}</a></td>
        <td><a href="/removeArtistFromFavourites/${artist.id}">Remove</a></td>
    </tr>
</c:forEach>
</table>
<span>Page: </span>
</body>
</html>