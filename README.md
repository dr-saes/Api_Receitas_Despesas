# Api de Receitas e Despesas (Controle financeiro doméstico)
## Tecnologias utilizadas:

-  Java.
-  Maven.
-  Eclipse.
-  SpringBoot.
-  MySql no desenvolvoimento e H2 para testes e deploy no Heroku.

## URI's para visualização pelo Heroku:

#### Todas as receitas:
https://app-receitas-despesas.herokuapp.com/receitas

#### Todas as despesas:
https://app-receitas-despesas.herokuapp.com/despesas



## Rotas:

### URL:  /auth 
    
##### Método: POST (autentica usuário)
##### BodyParams: Json com nome e senha: 	 
                                       
    {
      "nome": "davi",
      "senha": "123456"
    }   
###### Retorno: Bearer Token

### URL: /receitas
   
##### Método: POST (cadastra uma receita)
##### BodyParams: Json com dados da receitas 	 
    
     {
      "descricao": "Venda",
      "valorReceita": "2000",
      "dataReceita" : "01/03/2022"
     }
     
 
### URL: /receitas
##### Método: GET (retorna todas as receitas)

### URL: /receitas/{ano}/{mês}
##### Método: GET (retorna receitas por ano/ mês)

### URL: /receitas/{Id}
##### Método: GET (retorna receita por Id)

### URL: /receitas/{id}
##### Método: PUT (atualiza uma receita)
##### BodyParams: Json com dados da receitas 	 
  
    {
      "descricao": "Salário",
      "valorReceita": "10000",
      "dataReceita" : "01/03/2022"
    }

### URL: /receitas/{Id}
##### Método: DELETE (remove receitas por Id)

### URL: /despesas

##### Método: POST (cadastral uma despesa)
##### BodyParams: Json com dados da despesa 	
  
      {
        "descricao": "Conserto cano",
        "valorDespesa": "350",
        "dataDespesa": "10/01/2022",
        "tipoDespesa" : "IMPREVISTOS"
      }
   
###### (Se o TIPO não for mencionado é atribuído automaticamente o TIPO: OUTRAS.)

### URL: /despesas
##### Método: GET (retorna todas as despesas)

### URL: /despesas/{ano}/{mês}
##### Método: GET (retorna despesas por ano/ mês)

### URL: /despesas/{Id}
##### Método: GET (retorna despesa por Id)

### URL: /despesas/{id}
##### Método: PUT (atualiza uma despesa)
##### BodyParams: Json com dados da despesa 	 

      {
         "descricao": "Conserto cano",
         "valorDespesa": "350",
         "dataDespesa": "10/01/2022",
         "tipoDespesa" : "OUTRAS"
      }

### URL: /despesas/{Id}
##### Método: DELETE (remove despesas por Id)


### URL: /resumo/{ano}/{mês}
##### Método: GET (retorna o resumo das receitas e das despesas, o saldo final e as despesas separadas por TIPO)




     
     



