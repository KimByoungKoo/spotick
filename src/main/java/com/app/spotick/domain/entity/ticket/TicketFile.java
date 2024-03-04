package com.app.spotick.domain.entity.ticket;

import com.app.spotick.domain.base.image.ImageBase;
import com.app.spotick.domain.entity.place.Place;
import com.app.spotick.domain.entity.promotion.PromotionBoard;
import com.app.spotick.domain.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "TBL_TICKET_FILE")
@SequenceGenerator(name = "SEQ_TICKET_FILE_GENERATOR", sequenceName = "SEQ_TICKET_FILE",allocationSize = 1)
@Getter @ToString(callSuper = true) @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TicketFile extends ImageBase {
    @Id @GeneratedValue(generator = "SEQ_TICKET_FILE_GENERATOR")
    @Column(name = "TICKET_FILE_ID")
    private Long id;

    @OneToOne(mappedBy = "ticketFile", fetch = FetchType.LAZY)
    private Ticket ticket;

    @Builder
    public TicketFile(String fileName, String uuid, String uploadPath, Long id, Ticket ticket) {
        super(fileName, uuid, uploadPath);
        this.id = id;
        this.ticket = ticket;
    }
}
