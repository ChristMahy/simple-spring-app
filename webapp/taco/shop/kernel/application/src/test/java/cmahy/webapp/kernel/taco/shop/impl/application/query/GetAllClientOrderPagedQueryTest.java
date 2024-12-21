package cmahy.webapp.kernel.taco.shop.impl.application.query;

import cmahy.common.entity.page.EntityPageable;
import cmahy.common.helper.Generator;
import cmahy.webapp.taco.shop.kernel.application.mapper.output.ClientOrderPageOutputMapper;
import cmahy.webapp.taco.shop.kernel.application.query.GetAllClientOrderPagedQuery;
import cmahy.webapp.taco.shop.kernel.application.repository.ClientOrderPagingRepository;
import cmahy.webapp.taco.shop.kernel.domain.ClientOrder;
import cmahy.webapp.taco.shop.kernel.domain.page.ClientOrderPage;
import cmahy.webapp.taco.shop.kernel.vo.output.ClientOrderPageOutputVo;
import cmahy.webapp.user.kernel.application.repository.UserRepository;
import cmahy.webapp.user.kernel.domain.User;
import cmahy.webapp.user.kernel.domain.id.UserId;
import cmahy.webapp.user.kernel.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAllClientOrderPagedQueryTest {

    @Mock
    private UserRepository<User> userRepository;

    @Mock
    private ClientOrderPagingRepository<ClientOrder> clientOrderPagingRepository;

    @Mock
    private ClientOrderPageOutputMapper clientOrderPageOutputMapper;

    @InjectMocks
    private GetAllClientOrderPagedQuery getAllClientOrderPagedQuery;

    @Test
    void execute() {
        assertDoesNotThrow(() -> {
            UserId userId = new UserId(Generator.randomLongEqualOrAboveZero());
            User user = mock(User.class);
            EntityPageable entityPageable = mock(EntityPageable.class);
            ClientOrderPage<ClientOrder> clientOrderPage = mock(ClientOrderPage.class);
            ClientOrderPageOutputVo clientOrderPageOutputVo = mock(ClientOrderPageOutputVo.class);

            when(userRepository.findById(userId)).thenReturn(Optional.of(user));
            when(clientOrderPagingRepository.getByUser(user, entityPageable)).thenReturn(clientOrderPage);
            when(clientOrderPageOutputMapper.map(clientOrderPage)).thenReturn(clientOrderPageOutputVo);

            ClientOrderPageOutputVo actual = getAllClientOrderPagedQuery.execute(userId, entityPageable);

            assertThat(actual).isEqualTo(clientOrderPageOutputVo);
        });
    }

    @Test
    void execute_whenUserNotFound_thenThrowException() {
        assertThrows(UserNotFoundException.class, () -> {
            UserId userId = new UserId(Generator.randomLongEqualOrAboveZero());
            EntityPageable entityPageable = mock(EntityPageable.class);

            when(userRepository.findById(userId)).thenReturn(Optional.empty());

            getAllClientOrderPagedQuery.execute(userId, entityPageable);
        });
    }
}