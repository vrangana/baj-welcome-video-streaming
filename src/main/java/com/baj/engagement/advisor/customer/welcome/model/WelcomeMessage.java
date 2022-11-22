package com.baj.engagement.advisor.customer.welcome.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name ="WELCOME_MESSAGE")
@NamedQuery(name = "WelcomeMessage.findAll", query = "SELECT f FROM WelcomeMessage f ORDER BY f.channel")
public class WelcomeMessage {
    @Id
    @SequenceGenerator(name = "welcomeMessageSequence", sequenceName = "known_welcome_message_id_seq", allocationSize = 1, initialValue = 10)
    @GeneratedValue(generator = "welcomeMessageSequence")
    private Long id;
    private String languageCode;
    private String channel;
    private String welcomeMessage;
    /*
    public static Uni<WelcomeMessage> findByLanguageCode(String languageCodeParam) {
        return find("languageCode", languageCodeParam).firstResult();
    }
    */
}
