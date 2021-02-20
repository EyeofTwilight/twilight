package com.wangbaiwan.gravity.nglign.login.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * @author eyeoftwilight
 */
@Component
public class JwtTokenUtils
{
	private static final String CLAIM_KEY_USERNAME = "sub";
	private static final String CLAIM_KEY_ID = "id";
	private static final String CLAIM_KEY_CREATED = "created";
	private static final String CLAIM_KEY_ROLES = "roles";

	/**
	 * token的3中状态,假设token的有效期为t
	 * 1.在有效期内,无须刷新
	 * 2.当过期时间小于t时,认为是可刷新的
	 * 3.当过期时间大于t时,不可刷新,即已过期、无效的
	 * <p>
	 * 1.有效
	 */
	public static final int TOKEN_AVAILABLE = 1;

	/**
	 * 2.可刷新
	 */
	public static final int TOKEN_CAN_REFRESH = 2;

	/**
	 * 3.无效
	 */
	public static final int TOKEN_INVALID = 3;

	@Value("${jwt.token.secret}")
	private String secret;

	/**
	 * 过期时长，单位为秒,可以通过配置写入。
	 */
	@Value("${jwt.token.expiration}")
	private int expiration;

	/**
	 * 生成token
	 *
	 * @param claims
	 * @return
	 */
	public String generateToken(Map<String, Object> claims)
	{
		return this.generateToken2(claims, this.generateExpirationDate(expiration));
	}

	/**
	 * 生成2倍过期时间的token
	 *
	 * @param claims
	 * @return
	 */
	public String generateToken2Exp(Map<String, Object> claims)
	{
		return this.generateToken2(claims, this.generateExpirationDate(expiration * 2));
	}

	/**
	 * 生成token
	 *
	 * @param claims
	 * @return
	 */
	public String generateToken2(Map<String, Object> claims, Date exp)
	{
		return Jwts.builder().setClaims(claims).setExpiration(exp).signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	/**
	 * 根据token获取claims，其中含有用户信息等
	 *
	 * @param token
	 * @return
	 */
	private Claims getClaimsFromToken(String token)
	{
		Claims claims;
		try
		{
			claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		}
		catch (Exception e)
		{
			if (e instanceof ExpiredJwtException)
			{
				claims = ((ExpiredJwtException) e).getClaims();
			}
			else
			{
				claims = null;
			}
		}
		return claims;
	}


	/**
	 * 从token中获取用户名
	 *
	 * @param token
	 * @return
	 */
	public String getUserIdFromToken(String token)
	{
		String userId;
		try
		{
			userId = this.getClaimsFromToken(token).get("userId").toString();
		}
		catch (Exception e)
		{
			userId = null;
		}
		return userId;
	}

	/**
	 * 从token中获取token创建时间
	 *
	 * @param token
	 * @return
	 */
	public Date getCreatedDateFromToken(String token)
	{
		Date created;
		try
		{
			final Claims claims = getClaimsFromToken(token);
			created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
		}
		catch (Exception e)
		{
			created = null;
		}
		return created;
	}

	/**
	 * 从token中获取过期时间
	 *
	 * @param token
	 * @return
	 */
	public Date getExpirationDateFromToken(String token)
	{
		Date expiration;
		try
		{
			final Claims claims = this.getClaimsFromToken(token);
			expiration = claims.getExpiration();
		}
		catch (Exception e)
		{
			expiration = null;
		}
		return expiration;
	}


	/**
	 * 根据yml中的配置,生成过期时间
	 *
	 * @return
	 */
	private Date generateExpirationDate(int expiration)
	{
		return new Date(System.currentTimeMillis() + expiration * 1000);
	}

	/**
	 * 是否当前token,能刷新:
	 * 假设token的有效期为t
	 * 1.在有效期内,无须刷新
	 * 2.当过期时间小于t时,认为是可刷新的
	 * 3.当过期时间大于t时,不可刷新,即已过期、无效的
	 *
	 * @param token
	 * @return
	 */
	public int tokenStatus(String token)
	{
		if (token == null || "".equals(token))
		{
			return JwtTokenUtils.TOKEN_INVALID;
		}
		final Date expirationDate = this.getExpirationDateFromToken(token);
		if (expirationDate == null)
		{
			return JwtTokenUtils.TOKEN_INVALID;
		}
		// true: 已过期  false:未过期
		Date now = new Date();
		if (now.before(expirationDate))
		{
			return JwtTokenUtils.TOKEN_AVAILABLE;
		}
		long expiredTime = now.getTime() - expirationDate.getTime();
		if (expiredTime > expiration * 1000)
		{
			return JwtTokenUtils.TOKEN_INVALID;
		}
		return JwtTokenUtils.TOKEN_CAN_REFRESH;
	}

	/**
	 * token是否过期
	 *
	 * @param token
	 * @return true: 已过期  false:未过期
	 */
	private Boolean isTokenExpired(String token)
	{
		final Date expiration = this.getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	/**
	 * 刷新token
	 *
	 * @param token
	 * @return
	 */
	public String refreshToken(String token)
	{
		String refreshedToken;
		try
		{
			final Claims claims = getClaimsFromToken(token);
			claims.put(CLAIM_KEY_CREATED, new Date());
			refreshedToken = generateToken(claims);
		}
		catch (Exception e)
		{
			refreshedToken = null;
		}
		return refreshedToken;
	}
}
