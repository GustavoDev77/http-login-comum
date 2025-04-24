fetch('http://localhost:8080/auth/login', {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json',
    },
    body: JSON.stringify({
        email: email,
        senha: senha
    }),
    credentials: 'include'
})
.then(response => response.json())
.then(data => {
    console.log('Resposta do servidor:', data);
})
.catch(error => {
    console.error('Erro ao fazer login:', error);
});


