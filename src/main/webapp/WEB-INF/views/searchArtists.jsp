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
<form action="/searchArtists"><label>Search for: </label>
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
<h3>${nothingFound}</h3>
<span>Page: </span>
<c:choose>
    <c:when test="${totalPages <= 10 && totalPages > 0}">
        <c:forEach begin="1" end="${totalPages}" var="pageNr">
            <a href="/searchArtists?query=${query}&limit=${limit}&page=${pageNr}"><c:choose><c:when test="${pageNr == page}"><b>(${pageNr})</b></c:when><c:otherwise>${pageNr}</c:otherwise></c:choose></a>
        </c:forEach>
    </c:when>
    <c:when test="${page <= 6 && totalPages > 10}">
        <c:forEach begin="1" end="10" var="pageNr">
            <a href="/searchArtists?query=${query}&limit=${limit}&page=${pageNr}"><c:choose><c:when test="${pageNr == page}"><b>(${pageNr})</b></c:when><c:otherwise>${pageNr}</c:otherwise></c:choose></a>
        </c:forEach>
        ... <a href="/searchArtists?query=${query}&limit=${limit}&page=${totalPages}">${totalPages}</a>
    </c:when>
    <c:when test="${totalPages > 10 && page <= totalPages - 6 && page > 6}">
        <a href="/searchArtists?query=${query}&limit=${limit}&page=1">1</a> ...
        <c:forEach begin="${page - 4}" end="${page + 4}" var="pageNr">
            <a href="/searchArtists?query=${query}&limit=${limit}&page=${pageNr}"><c:choose><c:when test="${pageNr == page}"><b>(${pageNr})</b></c:when><c:otherwise>${pageNr}</c:otherwise></c:choose></a>
        </c:forEach>
        ... <a href="/searchArtists?query=${query}&limit=${limit}&page=${totalPages}">${totalPages}</a>
    </c:when>
    <c:when test="${totalPages > 10 && page > totalPages - 6}">
        <a href="/searchArtists?query=${query}&limit=${limit}&page=1">1</a> ...
        <c:forEach begin="${page - 5}" end="${totalPages}" var="pageNr">
            <a href="/searchArtists?query=${query}&limit=${limit}&page=${pageNr}"><c:choose><c:when test="${pageNr == page}"><b>(${pageNr})</b></c:when><c:otherwise>${pageNr}</c:otherwise></c:choose></a>
        </c:forEach>
    </c:when>
</c:choose>
</body>
</html>