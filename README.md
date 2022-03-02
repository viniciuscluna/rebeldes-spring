# Rebeldes API
## Documentação dos endpoints abaixo:
> Url base: http://localhost:8080

### Inserir rebelde

| Método | Endpoint |
| ------ | ------ |
| POST | rebeldes/inserir |

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

### Atualizar localização do rebelde

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

### Reportar Traidor

| Método | Endpoint |
| ------ | ------ |
| POST | rebeldes/reportarTraidor/{rebeldeId} |

> Reporta rebelde como traidor (São necessárias 3 chamada para um rebelde se efetivar como traidor), (Request é vazio):


### Negociar itens

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

## Relatórios

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

