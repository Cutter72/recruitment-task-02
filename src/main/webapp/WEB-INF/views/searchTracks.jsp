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
<form action="/searchTracks"><label>Search for: </label>
    <input type="text" name="query" value="${query}"/>
    <label>Items on page: </label>
    <select name="limit">
        <option value="10">10</option>
        <option value="20">20</option>
        <option value="30">30</option>
    </select>
    <button type="submit">Search</button>
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
<c:choose>
    <c:when test="${totalPages <= 10}">
        <c:forEach begin="0" end="${totalPages}" var="pageNr">
            <a href="/searchTracks?query=${query}&limit=${limit}&page=${pageNr}">${pageNr + 1}</a>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <c:choose>
            <c:when test="${page < 9}">
                <c:forEach begin="0" end="10" var="pageNr">
                    <a href="/searchTracks?query=${query}&limit=${limit}&page=${pageNr}">${pageNr + 1}</a>
                </c:forEach>
                ... <a href="/searchTracks?query=${query}&limit=${limit}&page=${totalPages}">${totalPages}</a>
            </c:when>
            <c:otherwise>
                <c:choose>
                    <c:when test="${page < totalPages - 4}">
                        <a href="/searchTracks?query=${query}&limit=${limit}&page=0">1</a> ...
                        <c:forEach begin="${totalPages - 5}" end="${totalPages - 1}" var="pageNr">
                            <a href="/searchTracks?query=${query}&limit=${limit}&page=${pageNr}">${pageNr + 1}</a>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <a href="/searchTracks?query=${query}&limit=${limit}&page=0">1</a> ...
                        <c:forEach begin="${page - 3}" end="${page + 3}" var="pageNr">
                            <a href="/searchTracks?query=${query}&limit=${limit}&page=${pageNr}">${pageNr + 1}</a>
                        </c:forEach>
                        ... <a href="/searchTracks?query=${query}&limit=${limit}&page=${totalPages}">${totalPages}</a>
                    </c:otherwise>
                </c:choose>
            </c:otherwise>
        </c:choose>
    </c:otherwise>
</c:choose>
</body>
</html>