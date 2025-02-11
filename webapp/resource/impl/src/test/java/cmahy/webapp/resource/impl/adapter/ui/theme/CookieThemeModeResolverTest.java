package cmahy.webapp.resource.impl.adapter.ui.theme;

import cmahy.common.helper.Generator;
import jakarta.servlet.http.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseCookie;
import org.springframework.web.util.WebUtils;

import static cmahy.webapp.resource.impl.adapter.ui.theme.CookieThemeModeResolver.THEME_MODE_REQUEST_ATTRIBUTE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Answers.RETURNS_SELF;
import static org.mockito.Answers.RETURNS_SELF;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CookieThemeModeResolverTest {

    private CookieThemeModeResolver cookieThemeModeResolver;

    private String cookieName;
    private String defaultThemeName;

    private MockedStatic<ResponseCookie> responseCookieMock;

    @Mock(answer = Answers.RETURNS_SELF)
    private ResponseCookie.ResponseCookieBuilder responseCookieBuilder;

    @Mock
    private ResponseCookie responseCookie;

    @BeforeEach
    void setUp() {
        assertDoesNotThrow(() -> {
            cookieName = Generator.generateAString();
            defaultThemeName = Generator.generateAString();

            responseCookieMock = Mockito.mockStatic(ResponseCookie.class);
            responseCookieMock.when(() -> ResponseCookie.from(cookieName)).thenReturn(responseCookieBuilder);
            when(responseCookieBuilder.build()).thenReturn(responseCookie);

            cookieThemeModeResolver = new CookieThemeModeResolver(cookieName, defaultThemeName);
        });
    }

    @AfterEach
    void tearDown() {
        assertDoesNotThrow(() -> {
            responseCookieMock.close();
        });
    }

    @Test
    void resolveThemeModeName() {
        assertDoesNotThrow(() -> {

            HttpServletRequest request = mock(HttpServletRequest.class);
            String requestAttribute = Generator.generateAString();

            when(request.getAttribute(THEME_MODE_REQUEST_ATTRIBUTE)).thenReturn(requestAttribute);

            String actual = cookieThemeModeResolver.resolveThemeModeName(request);

            assertThat(actual).isEqualTo(requestAttribute);

            verify(request, never()).setAttribute(anyString(), anyString());
        });
    }

    @Test
    void resolveThemeModeName_whenAttributeIsNotNull_thenJustReturn() {
        assertDoesNotThrow(() -> {

            try (MockedStatic<WebUtils> webUtilsMock = Mockito.mockStatic(WebUtils.class)) {

                String cookieName = Generator.generateAString();
                when(responseCookie.getName()).thenReturn(cookieName);
                HttpServletRequest request = mock(HttpServletRequest.class);

                webUtilsMock.when(() -> WebUtils.getCookie(request, cookieName)).thenReturn(null);

                cookieThemeModeResolver.resolveThemeModeName(request);

                verify(request).setAttribute(THEME_MODE_REQUEST_ATTRIBUTE, defaultThemeName);
            }
        });
    }

    @Test
    void resolveThemeModeName_whenCookieFromRequestIsNotNull_thenGetCookieFromRequestAndReturnResult() {
        assertDoesNotThrow(() -> {

            try (MockedStatic<WebUtils> webUtilsMock = Mockito.mockStatic(WebUtils.class)) {

                String cookieName = Generator.generateAString();
                when(responseCookie.getName()).thenReturn(cookieName);
                HttpServletRequest request = mock(HttpServletRequest.class);

                Cookie cookie = mock(Cookie.class);
                String cookieValue = Generator.generateAString();

                when(cookie.getValue()).thenReturn(cookieValue);

                webUtilsMock.when(() -> WebUtils.getCookie(request, cookieName)).thenReturn(cookie);

                cookieThemeModeResolver.resolveThemeModeName(request);

                verify(request).setAttribute(THEME_MODE_REQUEST_ATTRIBUTE, cookieValue);
            }
        });
    }

    @Test
    void setThemeMode() {
        assertDoesNotThrow(() -> {

            HttpServletRequest request = mock(HttpServletRequest.class);
            HttpServletResponse response = mock(HttpServletResponse.class);
            String themeMode = Generator.generateAString();

            when(responseCookie.mutate()).thenReturn(responseCookieBuilder);

            cookieThemeModeResolver.setThemeMode(request, response, themeMode);

            verify(request).setAttribute(THEME_MODE_REQUEST_ATTRIBUTE, themeMode);
        });
    }

    @Test
    void setThemeMode_whenThemeModeIsBlank_thenReplaceWithDefault() {
        assertDoesNotThrow(() -> {

            HttpServletRequest request = mock(HttpServletRequest.class);
            HttpServletResponse response = mock(HttpServletResponse.class);

            when(responseCookie.mutate()).thenReturn(responseCookieBuilder);

            cookieThemeModeResolver.setThemeMode(request, response, "     \t      ");

            verify(request).setAttribute(THEME_MODE_REQUEST_ATTRIBUTE, defaultThemeName);
        });
    }
}