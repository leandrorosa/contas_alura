<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<script src="https://code.jquery.com/jquery-2.0.2.js"></script>
</head>
<body>
    <table style="height: 10px; width: 775px;" border="1">
        <tr>
        <th>Código</th>
        <th>Descrição</th>
        <th>Valor</th>
        <th>Tipo</th>
        <th>Paga?</th>
        <th>Data de pagamento</th>
        <th>Alterar</th>
        <th>Remover</th>
        </tr>

        <c:forEach items="${contas}" var="conta">
        <tr>
            <td>${conta.id}</td>
            <td>${conta.descricao}</td>
            <td>${conta.valor}</td>
            <td>${conta.tipo}</td>
            <td id="conta_${conta.id}">
			  <c:if test="${conta.paga eq false}">
			    <a href="#" onClick="pagaAgora(${conta.id})">
			      Pagar agora!
			    </a>
			  </c:if>
			  <c:if test="${conta.paga eq true }">
			    Paga!
			  </c:if>
			</td>
            <td><fmt:formatDate value="${conta.dataPagamento.time}" pattern="dd/MM/yyyy"/></td>
            <td><a href="mostraConta?id=${conta.id}">Alterar</a></td>
            <td><a href="removeConta?id=${conta.id}">Remover</a></td>            
        </tr>        
        </c:forEach>
    </table>
	<script type="text/javascript">
	  function pagaAgora(id) {
	    $.post("pagaConta", {'id' : id}, function() {
	      $("#conta_"+id).html("Paga");	      
	    });
	  }
	</script>
</body>
</html>