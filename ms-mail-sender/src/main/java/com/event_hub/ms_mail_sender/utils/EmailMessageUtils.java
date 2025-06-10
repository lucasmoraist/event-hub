package com.event_hub.ms_mail_sender.utils;

import com.event_hub.ms_mail_sender.dto.ConfirmEmailData;
import com.event_hub.ms_mail_sender.dto.ConfirmInscriptionData;
import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.annotation.Value;

@UtilityClass
public class EmailMessageUtils {

    @Value("${app.environment.url}")
    private String url;

    public static String buildMessageToConfirm(ConfirmEmailData data) {
        return String.format("""
                <!DOCTYPE html>
                <html lang="pt-BR">
                <head>
                    <meta charset="UTF-8">
                    <title>Confirmação de E-mail</title>
                    <style>
                        body {
                            font-family: Arial, sans-serif;
                            background-color: #f4f6f8;
                            padding: 20px;
                        }
                        .container {
                            background-color: #ffffff;
                            border-radius: 8px;
                            padding: 30px;
                            max-width: 600px;
                            margin: 0 auto;
                            box-shadow: 0 4px 10px rgba(0,0,0,0.05);
                        }
                        h2 {
                            color: #2e86de;
                        }
                        p {
                            font-size: 16px;
                            color: #333333;
                        }
                        .footer {
                            margin-top: 20px;
                            font-size: 14px;
                            color: #999999;
                        }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <h2>Confirmação de E-mail</h2>
                        <p>Olá <strong>%s</strong>,</p>

                        <p>Para confirmar seu e-mail, clique no link abaixo:</p>
                        <p><a href="%s/confirm?email=%s">Confirmar E-mail</a></p>

                        <p>Atenciosamente,<br>
                        Equipe de Suporte</p>

                        <div class="footer">
                            Este é um e-mail automático. Por favor, não responda.
                        </div>
                    </div>
                </html>
                """, data.name(), url, data.to());
    }

    public static String buildMessageToInscription(ConfirmInscriptionData data) {
        return String.format("""
                        <!DOCTYPE html>
                        <html lang="pt-BR">
                        <head>
                            <meta charset="UTF-8">
                            <title>Confirmação de Presença</title>
                            <style>
                                body {
                                    font-family: Arial, sans-serif;
                                    background-color: #f4f6f8;
                                    padding: 20px;
                                }
                                .container {
                                    background-color: #ffffff;
                                    border-radius: 8px;
                                    padding: 30px;
                                    max-width: 600px;
                                    margin: 0 auto;
                                    box-shadow: 0 4px 10px rgba(0,0,0,0.05);
                                }
                                h2 {
                                    color: #2e86de;
                                }
                                p {
                                    font-size: 16px;
                                    color: #333333;
                                }
                                .footer {
                                    margin-top: 20px;
                                    font-size: 14px;
                                    color: #999999;
                                }
                            </style>
                        </head>
                        <body>
                            <div class="container">
                                <h2>Confirmação de Presença</h2>
                                <p>Olá <strong>%s</strong>,</p>

                                <p>Confirme sua presença no evento <strong>%s</strong>, que acontecerá em:</p>
                                <p><strong>Data:</strong> %s<br>
                                   <strong>Horário:</strong> %s<br>
                                   <strong>Local:</strong> %s</p>

                                <p>Clique no link abaixo para confirmar sua presença</p>
                                <p><a href="%s/inscriptions/confirm/%s">Confirmar Presença</a></p>

                                <p>Atenciosamente,<br>
                                Equipe de Organização</p>

                                <div class="footer">
                                    Este é um e-mail automático. Por favor, não responda.
                                </div>
                            </div>
                        </body>
                        </html>
                        """, data.userName(), data.userEmail(), data.eventDate(), data.eventTime(), data.eventLocation(), url,
                data.inscriptionId());
    }

}
