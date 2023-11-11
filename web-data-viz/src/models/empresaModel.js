var database = require('../database/config');

function listar() {
    var instrucao = `
        SELECT * FROM Empresa;
    `;
    console.log("Executando a instrução SQL: \n" + instrucao);
    return database.executar(instrucao);
}

function cadastrar(nomeEmpresa, razaoSocial, cnpj, emailRepresentante, senha, nomeRepresentante) {
    var instrucao = `
    
    INSERT INTO Empresa VALUES
	('${nomeEmpresa}', '${razaoSocial}', '${cnpj}');
   
    `;

    database.executar(instrucao);


    var instrucao2 = `
    Select IdEmpresa from empresa where CNPJ = '${cnpj}';

    `;

    console.log(instrucao2);

    var resultado = database.executar(instrucao2);

    console.log("resultado:" + JSON.stringify(resultado));

    if (resultado && resultado.length > 0) {
        var idEmpresa = resultado[0].IdEmpresa;

        var instrucao3 = `
        
        INSERT INTO Funcionario (idFuncionario, NomeFuncionario, Emailfuncionario, Senha, fkEmpresa, fkFuncao) VALUES ('${nomeRepresentante}', '${emailRepresentante}', '${senha}', ${idEmpresa}, 1);
        `;

        return database.executar(instrucao3);


    } else {
        return null;
    }
}

// function entrar(emailRepresentante, senha ) {
//     var instrucao = `
//         select * from Funcionario where EmailFuncionario = '${emailRepresentante}' and Senha = '${senha}';
//     `;
//     console.log("Executando a instrução SQL: \n" + instrucao);
//     return database.executar(instrucao);
// }


module.exports = {
    listar,
    cadastrar
}   