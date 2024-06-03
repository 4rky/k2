<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="model.UserBean"%>
<% if (session.getAttribute("registeredUser") == null) {
		response.sendRedirect("loginPage.jsp");
	}
%>
<jsp:useBean id="registeredUser" class="model.UserBean" scope="session"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
 	<title>Geek Factory - Vendita</title>
    <link rel="stylesheet" href="./css/account.css">
    <link rel="icon" href="./img/icon.png">
    <script>
        function validateForm() {
            const nomeProdotto = document.getElementById("nomeProdotto").value;
            const descrizione = document.getElementById("descrizione").value;
            const prezzo = document.getElementById("prezzo").value;
            const spedizione = document.getElementById("spedizione").value;
            
            const specialCharsRegex = /[<>\/\"]/;

            if (specialCharsRegex.test(nomeProdotto)) {
                alert("Il nome del prodotto non può contenere i seguenti caratteri: <, >, /, \"");
                return false;
            }

            if (specialCharsRegex.test(descrizione)) {
                alert("La descrizione non può contenere i seguenti caratteri: <, >, /, \"");
                return false;
            }

            if (isNaN(prezzo) || prezzo <= 0) {
                alert("Inserisci un prezzo valido.");
                return false;
            }

            if (isNaN(spedizione) || spedizione < 0) {
                alert("Inserisci un costo di spedizione valido.");
                return false;
            }

            return true;
        }
    </script>
</head>
<body>
	<div class="header">
		<jsp:include page="header.jsp"/>
	</div>
	
	<div class="test">
	<div class="container" style="height: 540px">
		<div class="title">Inserisci informazioni sul prodotto</div>
		<div class="content">
		    <form action="VenditaS" enctype="multipart/form-data" method="POST" onsubmit="return validateForm()">
        <div class="user-details">
            <div class="input-box">
                <span class="details">Nome prodotto</span>
                <input type="text" id="nomeProdotto" name="nome" maxlength="50" placeholder="Inserire nome prodotto" autofocus required/>
            </div>
            <div class="input-box">
                <span class="details">Prezzo</span>
                <input type="number" step="0.01" id="prezzo" name="prezzo" max="9999999" placeholder="Inserire prezzo" required/>
            </div>
            <div class="input-box">
                <span class="details">Spese di spedizione</span>
                <input type="number" step="0.01" id="spedizione" name="spedizione" max="999" placeholder="Inserire spese di spedizione" required/>
            </div>
            <div class="input-box">
                <span class="details">Immagine</span>
                <input type="file" id="immagine" name="immagine" accept="image/png, image/gif, image/jpeg" required>
            </div>
            <div class="input-box">
                <span class="details">Tipologia</span>
                <select id="tipologia" name="tipologia" required>
                    <option value="Action Figures">Action Figures</option>
                    <option value="Gadget">Gadget</option>
                    <option value="Arredamento Casa">Arredamento Casa</option>
                </select>
            </div>
            <div class="input-box">
                <span class="details">Tag</span>
                <select id="tag" name="tag" required>
                    <option value="Manga/Anime">Manga/Anime</option>
                    <option value="Film/Serie TV">Film/Serie TV</option>
                    <option value="Videogiochi">Videogiochi</option>
                    <option value="Originali">Originali</option>
                </select>
            </div>
            <div class="input-box">
                <span class="details">Descrizione</span>
                <textarea id="descrizione" name="descrizione" rows="4" cols="60" style="resize: none; width: 450px; height: 80px" required></textarea>
            </div>
        </div>
        <div class="button">
            <input type="submit" class="vendi" name="vendi" id="vendi" value="Vendi il prodotto" style="margin-bottom: 40px">
        </div>
    </form>
		
	</div>
	</div>
	</div>
	
	<div class="footer">
		<jsp:include page="footer.jsp"/>
	</div>
</body>

</html>