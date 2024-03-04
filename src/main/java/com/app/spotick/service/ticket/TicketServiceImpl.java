package com.app.spotick.service.ticket;

import com.app.spotick.domain.dto.ticket.TicketDetailDto;
import com.app.spotick.domain.dto.ticket.TicketGradeSaleInfoDto;
import com.app.spotick.domain.dto.ticket.TicketListDto;
import com.app.spotick.domain.dto.ticket.TicketRegisterDto;
import com.app.spotick.domain.entity.ticket.Ticket;
import com.app.spotick.domain.entity.ticket.TicketFile;
import com.app.spotick.domain.entity.ticket.TicketGrade;
import com.app.spotick.domain.entity.user.User;
import com.app.spotick.domain.type.post.PostStatus;
import com.app.spotick.repository.ticket.TicketRepository;
import com.app.spotick.repository.ticket.grade.TicketGradeRepository;
import com.app.spotick.repository.user.UserRepository;
import com.app.spotick.service.ticket.file.TicketFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final TicketGradeRepository ticketGradeRepository;
    private final TicketFileService ticketFileService;
    private final UserRepository userRepository;
    @Override
    public void registerTicket(TicketRegisterDto ticketRegisterDto, Long userId) throws IOException {
        User tmpUser = userRepository.getReferenceById(userId);

        // 티켓 이벤트 파일 저장
        TicketFile ticketFile = ticketFileService.registerAndSaveTicketFile(ticketRegisterDto.getTicketFile());

        Ticket ticketEntity = ticketRegisterDto.toEntity();
        ticketEntity.setUser(tmpUser);
        ticketEntity.setStatus(PostStatus.REGISTRATION_PENDING);
        ticketEntity.setFile(ticketFile);

        // 티켓 이벤트 db저장
        Ticket savedTicket = ticketRepository.save(ticketEntity);


        // ticketGrades를 엔티티 타입으로 변환 후 entity리스트로 변환 -> 이후 saveAll
        List<TicketGrade> ticketGradeEntities = ticketRegisterDto.getTicketGrades().stream()
                .map(dto -> {
                    TicketGrade ticketGradeEntity = dto.toEntity();
                    ticketGradeEntity.setTicket(savedTicket);
                    return ticketGradeEntity;
                })
                .toList();
        ticketGradeRepository.saveAll(ticketGradeEntities);
    }

    @Override
    public Slice<TicketListDto> findTicketListPage(Pageable pageable, Long userId) {
        return ticketRepository.findTicketListPage(pageable, userId);
    }

    @Override
    public List<TicketGradeSaleInfoDto> findTicketGrades(Long ticketId, LocalDate date) {
        return ticketGradeRepository.findTicketGradesByTicketId(ticketId, date);
    }

    @Override
    public TicketDetailDto findTicketDetailById(Long ticketId, Long userId) {
        TicketDetailDto content = ticketRepository.findTicketDetailById(ticketId, userId).orElseThrow(
                NoSuchElementException::new
        );

        return content;
    }
}
