function registrar() {
    var nome = document.getElementById('nome').value;
    var email = document.getElementById('email').value;
    var senha = document.getElementById('senha').value;
    var mensagemDiv = document.getElementById('mensagem');

    mensagemDiv.innerHTML = '';
    mensagemDiv.classList.remove("error", "success");

    if (!nome || !email || !senha) {
        mensagemDiv.innerHTML = "Por favor, preencha todos os campos.";
        mensagemDiv.classList.add("error");
        return;
    }

    fetch('http://localhost:8080/auth/registrar', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            nome: nome,
            email: email,
            senha: senha
        }),
        credentials: 'include'
    })
    .then(response => {
        if (response.ok) {
            return response.text();
        } else {
            return response.text().then(text => {
                throw new Error(text);
            });
        }
    })
    .then(data => {
            mensagemDiv.innerHTML = "Conta criada com sucesso! Aguarde...";
            mensagemDiv.classList.remove("error");
            mensagemDiv.classList.add("success");

            setTimeout(function() {
                window.location.href = "index.html";
            }, 3000);
        })
        .catch(error => {
            console.error('Erro ao registrar usuário:', error);
            mensagemDiv.innerHTML = "Ocorreu um erro. Tente novamente mais tarde.";
            mensagemDiv.classList.add("error");
        });
    }

