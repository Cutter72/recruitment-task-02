<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pl-PL">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="X-UA-Compatible" content="ie=edge"/>
    <title>Spotify artist search</title>
</head>
<body>
<h1>Search for artist:</h1>
<form action="/searchArtists">
    <input type="text" name="query" value="${query}"/>
    <button type="submit">Szukaj</button>
</form>
<table>
    <tr>
        <th>Name</th>
        <th>Add to favourites</th>
    </tr>
    <c:forEach items="${searchedArtists}" var="artist">
        <tr style="height: 50px">
            <td><a href="${artist.externalUrls.externalUrls.get('spotify')}" target="_blank">${artist.name}</a></td>
            <td>ADD/REMOVE</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>