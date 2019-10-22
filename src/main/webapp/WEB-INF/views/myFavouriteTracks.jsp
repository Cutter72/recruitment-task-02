<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pl-PL">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="X-UA-Compatible" content="ie=edge"/>
    <title>My favourite tracks</title>
</head>
<body>
<a href="/">back</a>
<h1>My favourite tracks</h1>
<table>
    <tr>
        <th>Title</th>
        <th>Artist</th>
        <th>Album</th>
        <th>previewUrl</th>
        <th>spotifyUrl</th>
        <th>Remove from favourites</th>
    </tr>
    <c:forEach items="${tracks}" var="track">
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
            <td><a href="${track.externalUrls.externalUrls.get('spotify')}" target="_blank">Listen on Spotify</a></td>
            <td><a href="/removeTrackFromFavourites/${track.id}">Remove</a></td>
        </tr>
    </c:forEach>
</table>
<span>Page: </span>
</body>
</html>