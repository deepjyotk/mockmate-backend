//// src/main/java/com/mockmate/auth_service/config/WebSocketConfig.java
//package com.mockmate.auth_service.config;
//
//import com.mockmate.auth_service.security.jwt.JwtTokenUtil;
//import com.mockmate.auth_service.service.CustomUserDetailsService;
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.http.server.ServerHttpResponse;
//import org.springframework.http.server.ServletServerHttpRequest;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.MessageChannel;
//import org.springframework.messaging.simp.config.ChannelRegistration;
//import org.springframework.messaging.simp.config.MessageBrokerRegistry;
//import org.springframework.messaging.simp.stomp.StompCommand;
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
//import org.springframework.messaging.support.ChannelInterceptor;
//import org.springframework.messaging.support.MessageHeaderAccessor;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.web.socket.WebSocketHandler;
//import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
//import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
//import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
//import org.springframework.web.socket.server.HandshakeInterceptor;
//
//import java.util.Map;
//
//@Configuration
//@EnableWebSocketMessageBroker
//@Order(Ordered.HIGHEST_PRECEDENCE + 99)
//public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
//
//    private final WebSocketHandshakeInterceptor handshakeInterceptor;
//    private final JwtTokenUtil jwtTokenUtil;
//    private final CustomUserDetailsService customUserDetailsService ;
//
//
//    @Autowired
//    public WebSocketConfig(WebSocketHandshakeInterceptor handshakeInterceptor, JwtTokenUtil jwtTokenUtil, CustomUserDetailsService customUserDetailsService) {
//        this.handshakeInterceptor = handshakeInterceptor;
//        this.jwtTokenUtil = jwtTokenUtil ;
//        this.customUserDetailsService = customUserDetailsService ;
//    }
//
//
//
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry config) {
//        // Enables a simple in-memory broker with /topic and /queue prefixes
//        config.enableSimpleBroker("/topic");
//        // Prefix for messages bound for @MessageMapping methods
//        config.setApplicationDestinationPrefixes("/app");
//        // Prefix for user-specific messages
////        config.setUserDestinationPrefix("/user");
//    }
//
//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/ws1")
//                .addInterceptors(httpSessionHandshakeInterceptor())
//                .setAllowedOrigins("http://localhost:3000") // Add your frontend origins here
//
//                .withSockJS() ;
//
//
//    }
//    @Bean
//    public HandshakeInterceptor httpSessionHandshakeInterceptor() {
//        return new HandshakeInterceptor() {
//            @Override
//            public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
//                if (request instanceof ServletServerHttpRequest) {
//                    ServletServerHttpRequest servletServerRequest = (ServletServerHttpRequest) request;
//                    HttpServletRequest servletRequest = servletServerRequest.getServletRequest();
//                    String token = "";
//                    if(token==null){
//                        return false;
//                    }
//                    return false;
////                    attributes.put("username", tokenService.getUsernameFromJWT(token));
//                }
//                return true;
//            }
//            @Override
//            public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
//            }
//        };
//    }
//
//
//    @Override
//    public void configureClientInboundChannel(ChannelRegistration registration) {
//        registration.interceptors(new ChannelInterceptor() {
//            @Override
//            public Message<?> preSend(Message<?> message, MessageChannel channel) {
//                System.out.println("message is: "+ message);
//                StompHeaderAccessor accessor =
//                        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
//
//                assert accessor != null;
//                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
//
//                    String authorizationHeader = accessor.getFirstNativeHeader("Authorization");
//                    assert authorizationHeader != null;
//                    String token = authorizationHeader.substring(7);
//
//                    String username = jwtTokenUtil.getUsernameFromToken(token);
//
//                    UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
//
//
//                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//
//                    accessor.setUser(usernamePasswordAuthenticationToken);
//                }
//
//                return message;
//            }
//
//        });
//    }
//}
