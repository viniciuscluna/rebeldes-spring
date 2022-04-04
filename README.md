# Rebeldes API
## Documentação dos endpoints abaixo:
> Url base: http://localhost:8080

### Login
Por padrão o sistema cria um usuário master com as seguintes credenciais:

| Login | Senha |
| ------ | ------ |
| admin | admin |

Request:

| Método | Endpoint |
| ------ | ------ |
| POST | api/login |

> Realiza login na aplicação:
> 
>### Método: x-www-form-urlencoded
> 
```sh
username : admin
password: admin
```


### Listar Rebeldes (Admin Only)
Para paginar utilize os seguintes parâmetros abaixo:

| Parâmetro | Descrição | Exemplo |
| ------ | ------ |------ |
| sortBy | O campo a ser filtrado | nome |
| page | Página selecionada (Inicia com 0) | 0 |
| size | (Quantidade de itens por página) | 10 |
| direction | Direção dos dados (DESC = Maior para menor, ASC = Menor para maior) | DESC |

Request:

| Método | Endpoint | Exemplo
| ------ | ------ | ------ |
| POST | rebeldes | rebeldes?sortBy=nome&page=0&size=10&direction=DESC

Exemplo de resposta
```sh
{
    "content": [
        {
            "id": 3,
            "nome": "Teste3",
            "idade": 40,
            "genero": "Humano",
            "traidor": false,
            "localizacao": {
                "id": 2,
                "latitude": "12456",
                "longitude": "1234567",
                "nomeGalaxia": "terra"
            },
            "itens": []
        }
    ],
    "pageable": {
        "sort": {
            "empty": false,
            "sorted": true,
            "unsorted": false
        },
        "offset": 0,
        "pageNumber": 0,
        "pageSize": 10,
        "unpaged": false,
        "paged": true
    },
    "last": true,
    "totalElements": 3,
    "totalPages": 1,
    "size": 10,
    "number": 0,
    "sort": {
        "empty": false,
        "sorted": true,
        "unsorted": false
    },
    "first": true,
    "numberOfElements": 3,
    "empty": false
}
```

### Inventário Rebelde (Admin Only)
Traz o inventário do rebelde juntamente com seu nome:

Request:

| Método | Endpoint 
| ------ | ------ 
| POST | rebeldes/{idRebelde}/inventario

Exemplo de resposta
```sh
{
    "rebelde": "Teste3",
    "itens": [
        {
            "id": 7,
            "nome": "Arma",
            "pontos": 5
        },
        {
            "id": 8,
            "nome": "Comida",
            "pontos": 10
        }
    ]
}
```

### Inserir rebelde (Admin Only)

| Método | Endpoint |
| ------ | ------ |
| POST | rebeldes |

> Insere o rebelde na base de dados, exemplo de request:
```sh
{
    "nome": "Teste",
    "idade": 40,
    "genero": "M",
    "localizacao": {
        "latitude": "12456",
        "longitude": "1234567",
        "nomeGalaxia": "terra"
    },
    "itens": [
        {
            "nome": "Arma",
            "pontos": 5
        },
        {
            "nome": "Comida",
            "pontos": 10
        }
    ]
}
```

### Atualizar rebelde (Admin Only)

| Método | Endpoint |
| ------ | ------ |
| PUT | rebeldes |

> Insere o rebelde na base de dados, exemplo de request (Id é obrigatório):
```sh
{
    "id":2,
    "nome": "Teste",
    "idade": 40,
    "genero": "M",
    "localizacao": {
        "latitude": "12456",
        "longitude": "1234567",
        "nomeGalaxia": "terra"
    },
    "itens": [
        {
            "nome": "Arma",
            "pontos": 5
        },
        {
            "nome": "Comida",
            "pontos": 10
        }
    ]
}
```

### Remover rebelde (Admin Only)

| Método | Endpoint |
| ------ | ------ |
| DELETE | rebeldes/{idRebelde} |

> Remove o rebelde na base de dados (Request vazio)

### Atualizar localização do rebelde (Rebelde Only)

| Método | Endpoint |
| ------ | ------ |
| PUT | rebeldes/atualizaLocalizacao/{rebeldeId} |

> Atualiza localização do rebelde, exemplo de request:
```sh
{
    "latitude":"1111",
    "longitude":"2222",
    "nomeGalaxia": "Terra"
}
```

### Reportar Traidor (Rebelde Only)

| Método | Endpoint |
| ------ | ------ |
| POST | rebeldes/reportarTraidor/{rebeldeId} |

> Reporta rebelde como traidor (São necessárias 3 chamada para um rebelde se efetivar como traidor), (Request é vazio):


### Negociar itens (Rebelde Only)

| Método | Endpoint |
| ------ | ------ |
| POST | rebeldes/negociar |

> Negocia itens do inventário de 2 rebeldes, exemplo do request :
> Nota: Ambos precisam possuir mesma quantidade de pontos
```sh
{
    "rebeldeAId": 1,
    "rebeldeBId": 2,
    "itensrebeldeA": [
        1,
        2
    ],
    "itensrebeldeB": [
        3,
        4
    ]
}
```

## Relatórios (Rebelde e Admin Only)

### Porcentagem de rebeldes

| Método | Endpoint |
| ------ | ------ |
| GET | rebeldesRelatorio/pctRebeldes |

> Retorna a porcentagem de rebeldes inseridos, exemplo de resposta
```sh
40
```
### Porcentagem de traidores

| Método | Endpoint |
| ------ | ------ |
| GET | rebeldesRelatorio/pctTraidores |

> Retorna a porcentagem de traidores inseridos, exemplo de resposta
```sh
50
```

### Tipo de recursos dos Rebeldes

| Método | Endpoint |
| ------ | ------ |
| GET | rebeldesRelatorio/tipoRecurso |

> Retorna uma relação de recurso compartilhado entre os rebeldes. Exemplo de resposta:
```sh
[
    {
        "nomeRecurso": "Comida",
        "quantidade": 1
    },
    {
        "nomeRecurso": "Arma",
        "quantidade": 1
    }
]
```

### Pontos perdidos aos traidores

| Método | Endpoint |
| ------ | ------ |
| GET | rebeldesRelatorio/pontosPerdidos |

> Retorna o total de pontos dos itens perdidos dos traidores:
```sh
40
```


## Projeto desenvolvido por Vinicius Luna

> Link do projeto:
**<https://github.com/viniciuscluna/rebeldes-spring>**

