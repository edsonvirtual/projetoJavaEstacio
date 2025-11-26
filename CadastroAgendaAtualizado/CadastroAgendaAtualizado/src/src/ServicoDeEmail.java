package src;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class ServicoDeEmail {

    // --- CONFIGURE AQUI ---
    // O e-mail que vai ENVIAR as mensagens (ex: seu email do gmail)
    private static final String MEU_EMAIL_REMETENTE = "seu-email@gmail.com"; 
    
    // A "Senha de App" de 16 letras que você gerou no Google
    private static final String MINHA_SENHA_DE_APP = "suasenhadeappde16letras"; 
    
    private static final String HOST_SMTP = "smtp.gmail.com";
    private static final int PORTA_SMTP = 587;
    // --------------------

    public void enviarEmail(String para, String assunto, String corpoEmail) throws EmailException {
        
        System.out.println("Iniciando tentativa de envio de e-mail para: " + para);

        Email email = new SimpleEmail();
        email.setHostName(HOST_SMTP);
        email.setSmtpPort(PORTA_SMTP);
        
        // Autenticação
        email.setAuthenticator(new DefaultAuthenticator(MEU_EMAIL_REMETENTE, MINHA_SENHA_DE_APP));
        email.setStartTLSEnabled(true); // Habilita conexão segura
        
        // Dados da Mensagem
        email.setFrom(MEU_EMAIL_REMETENTE, "Sua Agenda de Eventos");
        email.setSubject(assunto);
        email.setMsg(corpoEmail);
        email.addTo(para); // O e-mail do destinatário
        
        // Envia
        email.send();
        
        System.out.println("E-mail enviado com sucesso!");
    }
}