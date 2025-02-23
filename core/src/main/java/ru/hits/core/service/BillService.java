package ru.hits.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hits.common.dtos.bill.BillCreateDTO;
import ru.hits.common.dtos.bill.BillResponseDTO;
import ru.hits.common.dtos.bill.Status;
import ru.hits.common.dtos.bill.TransactionResponseDTO;
import ru.hits.common.security.JwtUserData;
import ru.hits.common.security.exception.BadRequestException;
import ru.hits.core.entity.BillEntity;
import ru.hits.core.feignClient.UserClient;
import ru.hits.core.repository.BillRepository;
import ru.hits.core.service.interfaces.IBillService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BillService implements IBillService {
    private  final UserClient userClient;
    private final BillRepository billRepository;

    @Transactional
    @Override
    public BillResponseDTO create(BillCreateDTO billCreateDTO, Authentication authentication) {
        JwtUserData user = (JwtUserData) authentication.getPrincipal();
        if(!userClient.isUserExists(user.getId()) || userClient.isAdmin(user)){
            throw new BadRequestException("Пользователь не может создать счет");
        }
        BillEntity bill = new BillEntity(
                UUID.randomUUID(),
                user.getId(),
                0,
                billCreateDTO.getType(),
                Status.OPEN,
                billCreateDTO.getName()
        );
        billRepository.save(bill);
        return new BillResponseDTO(
                bill.getId(),
                user.getId(),
                bill.getAmount(),
                bill.getType(),
                bill.getStatus()
        );
    }

    @Override
    public BillResponseDTO getBillInfo(UUID id, Authentication authentication) {
        return null;
    }

    @Override
    public List<TransactionResponseDTO> getBillTransactions(UUID billId, Authentication authentication) {
        return null;
    }

    @Override
    public void closeBill(UUID id, Authentication authentication) {

    }
}
