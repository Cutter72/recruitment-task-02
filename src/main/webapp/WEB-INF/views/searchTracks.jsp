<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pl-PL">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="X-UA-Compatible" content="ie=edge"/>
    <title>Spotify track search</title>
</head>
<body>
<h1>Search for track:</h1>
<form action="/searchTracks">
    <input type="text" name="query" value="${query}"/>
    <button type="submit">Szukaj</button>
</form>
<table>
    <tr>
        <th>Title</th>
        <th>Artist</th>
        <th>Album</th>
        <th>previewUrl</th>
        <th>Add to favourites</th>
    </tr>
    <c:forEach items="${searchedTracks}" var="track">
        <tr style="height: 50px">
            <td>${track.name}</td>
            <td>
                <c:forEach items="${track.artists}" var="artist">
                    ${artist.name},
                </c:forEach>
            </td>
            <td>${track.album.name}</td>
            <td><c:choose>
                <c:when test="${track.previewUrl != null}">
                    <video controls name="media" height="40px" width="300px">
                        <source src="${track.previewUrl}" type="audio/mpeg">
                    </video>
                </c:when>
                <c:otherwise>
                    Preview not supported.
                </c:otherwise>
            </c:choose></td>
            <td>ADD/REMOVE</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>