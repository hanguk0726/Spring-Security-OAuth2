package com.posts.config.auth;//package com.openshy4j.config.auth;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.openshy4j.service.IdentityServiceImpl;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jws;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
//import java.security.Key;
//import lombok.NoArgsConstructor;
//import lombok.RequiredArgsConstructor;
//import org.openstack4j.model.identity.v3.Token;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import javax.servlet.http.HttpServletRequest;
//import java.util.Base64;
//import java.util.Date;
//import java.util.List;
//@NoArgsConstructor
//@Component
//public class JwtTokenProvider {
//
//  private Key secretKey;
//
//  // 토큰 유효시간 4시간
//  private long tokenValidTime = 4 * 60 * 60 * 1000L;
//
//  @Autowired
//  @Qualifier("UserDetailsServiceImpl")
//  private UserDetailsService userDetailsService;
//
//  @Autowired
//  private IdentityServiceImpl identityService;
//
//  // 객체 초기화
//  @PostConstruct
//  protected void init() {
//    secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//  }
//
//  // JWT 토큰 생성
//  public String createToken(String userPk, String role) throws JsonProcessingException {
//    Claims claims = Jwts.claims().setSubject(userPk); // JWT payload 에 저장되는 정보단위
//    claims.put("role", role); // 정보는 key / value 쌍으로 저장된다.
//    Date now = new Date();
//    Token token = identityService.getToken();
//    ObjectMapper objectMapper = new ObjectMapper();
//    String tokenAsJson = objectMapper.writeValueAsString(token);
//    claims.put("token",tokenAsJson);
//    return Jwts.builder()
//        .setClaims(claims) // 정보 저장
//        .setIssuedAt(now) // 토큰 발행 시간 정보
//        .setExpiration(new Date(now.getTime() + tokenValidTime)) // set Expire Time
//        .signWith(secretKey)
//        .compact();
//  }
//
//  // JWT 토큰에서 인증 정보 조회
//  public Authentication getAuthentication(String token) {
//    UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
//    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
//  }
//
//  // 토큰에서 회원 정보 추출
//  public String getUserPk(String token) {
//    return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
//  }
//
//
//  // 토큰의 유효성 + 만료일자 확인
//  public boolean validateToken(String jwtToken) {
//    try {
//      Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwtToken);
//      return !claims.getBody().getExpiration().before(new Date());
//    } catch (Exception e) {
//      return false;
//    }
//  }
//}
