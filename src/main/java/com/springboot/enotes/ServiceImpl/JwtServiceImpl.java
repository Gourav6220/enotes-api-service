package com.springboot.enotes.ServiceImpl;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import com.springboot.enotes.Entity.User;
import com.springboot.enotes.Service.JwtService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
@Service
public class JwtServiceImpl implements JwtService {

private String secretKey="";

public JwtServiceImpl(){
	try {
		
		KeyGenerator genKey = KeyGenerator.getInstance("HmacSHA256");
		SecretKey sk = genKey.generateKey();
		secretKey=Base64.getEncoder().encodeToString(sk.getEncoded());
	}catch(Exception ex) {
		ex.printStackTrace();
	}
	
}


	@Override
	public String generateToken(User user) {
	
		Map<String,Object> claims=new HashMap<>();
		claims.put("id", user.getId());
		claims.put("roles", user.getRoles());
		claims.put("status", user.getStatus().getIsActive());
		String token = Jwts.builder().claims().add(claims).subject(user.getEmail())
		.issuedAt(new Date(System.currentTimeMillis()))
		.expiration(new Date(System.currentTimeMillis()+60*60*10))
		.and().signWith(getKey())
		.compact();
		
		return token;
	}

	private Key getKey() {
		byte[] decode = Decoders.BASE64.decode(secretKey);
		
		return Keys.hmacShaKeyFor(decode);
	}

	
}
