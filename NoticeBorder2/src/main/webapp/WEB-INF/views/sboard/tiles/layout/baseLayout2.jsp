<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
<head>



<title>NoticeBoard</title>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="/resources/css/base.css" />
<link rel="stylesheet" type="text/css" href="/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="/resources/css/sub.css" />
<script src="http://code.jquery.com/jquery-3.3.1.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7/jquery.js"></script>

</head>
<body>
	<div class="wrap">
		<header class="commonHeader">
			<tiles:insertAttribute name="header" />
		</header>
		<main>
			<tiles:insertAttribute name="body" />
			<div class="loadingWrap">
				<div class="loading"></div>
			</div>
		</main>
		
	</div>

</body>
</html>