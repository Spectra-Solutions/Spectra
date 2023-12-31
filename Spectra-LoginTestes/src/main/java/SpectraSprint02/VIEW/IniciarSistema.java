package SpectraSprint02.VIEW;

import SpectraSprint02.DAO.*;
import SpectraSprint02.DTO.Funcionario;
import SpectraSprint02.DTO.Maquina;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class IniciarSistema {
    FuncionarioDao validarFuncionario = new FuncionarioDao();
    MaquinaDao maquinaDao = new MaquinaDao();
    Maquina maquina = new Maquina();
    Funcionario func = new Funcionario();
    MemoriaRamDao memoriaRamDao = new MemoriaRamDao();
    RedeDao redeDao = new RedeDao();
    CPUDao cpuDao = new CPUDao();
    DiscoDao discoDao = new DiscoDao();
    ProcessoDao processoDao = new ProcessoDao();
    Timer timer = new Timer();

    public void validarLogin(){
        String emailJframe, senhaJframe;
        Integer comprimentoMinino = 8;

        emailJframe = JOptionPane.showInputDialog("""
                Digite seu email:""");
        func.setEmail(emailJframe);

        senhaJframe = JOptionPane.showInputDialog("""
                Digite sua senha:""");
        func.setSenha(senhaJframe);

        if (emailJframe.equals("")){
            JOptionPane.showMessageDialog(null, "Não pode deixar o campo vazio");
        }

        else
        {
            if (emailJframe.indexOf("@") >= 0 && emailJframe.indexOf(".") >= 0 && senhaJframe.length() >= comprimentoMinino) {

                if (validarFuncionario.existEmail(func.getEmail(), func.getSenha())) {
                    JOptionPane.showMessageDialog(null, "Usuário logado com sucesso!!");
                    validarMaquina();
                } else {
                    JOptionPane.showMessageDialog(null, "Usuário não existe no sistema!!");
                    validarLogin();
                }

            }
            else {
                JOptionPane.showMessageDialog(null, "Falta @ no seu email!! ou falta . no seu email!! ou sua senha está incorreta!!");
            }
        }
    }

    public void validarMaquina(){
        String nomeJframe, secaoJframe;

        if (maquinaDao.existHostName(maquina.getHostName())){
            System.out.println("Maquina existe, não precisa fazer um novo cadastro");
            capturarDados();
        } else {
            nomeJframe = JOptionPane.showInputDialog("""
                Digite o nome que você quer atribuir a maquina:""");
            maquina.setNome(nomeJframe);

            secaoJframe = JOptionPane.showInputDialog("""
                Digite a seção que a máquina se encontra: """);
            maquina.setSecao(secaoJframe);

            maquinaDao.getFkEmpresa(maquina.getNome(), maquina.getSecao(),func.getEmail(), func.getSenha());

            JOptionPane.showMessageDialog(null, """
                    Cadastrando nova maquina...
                    """);

            JOptionPane.showMessageDialog(null, """
                    Adicionando dados a maquina....
                    """);
            capturarDados();
        }
    }

    public void capturarDados(){
        final  long SEGUNDOS = (1000 * 5);

        TimerTask tarefa = new TimerTask() {
            @Override
            public void run() {
                cpuDao.getFkComponenteCPU();
                memoriaRamDao.getFkComponenteRAM();
                discoDao.getFkComponenteDisco();
                redeDao.getFkComponenteRede();
                processoDao.getfkMaquina();
            }
        };

        timer.scheduleAtFixedRate(tarefa, 0, SEGUNDOS);
    }
}
