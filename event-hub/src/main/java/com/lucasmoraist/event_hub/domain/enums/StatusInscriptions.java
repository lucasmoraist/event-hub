package com.lucasmoraist.event_hub.domain.enums;

public enum StatusInscriptions {
    PENDING, // Inscrição realizada, mas ainda não confirmada (ex: precisa validar e-mail).
    CONFIRMED, // Inscrição confirmada, o usuário está oficialmente inscrito.
    CANCELLED, // Inscrição cancelada pelo usuário ou por um admin.
    WAITING_LIST, // O evento está cheio, o usuário está em lista de espera.
    CHECKED_IN, //Presença confirmada no evento (ex: marcou presença no dia).
    EXPIRED // Inscrição expirada (ex: evento já passou e o usuário não compareceu).
}
