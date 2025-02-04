package com.dinethbakers.hrm.aop;

import com.dinethbakers.hrm.aop.annotations.RequireRole;
import com.dinethbakers.hrm.entity.UserEntity;
import com.dinethbakers.hrm.exceptions.UnauthorizedException;
import com.dinethbakers.hrm.repository.jparepository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.Optional;

@Aspect
@Configuration
@RequiredArgsConstructor
public class RequireRoleAspect {
    private final UserRepository userRepository;

    @Around("com.dinethbakers.hrm.aop.CommonPointcutConfig.requireRole()")
    public Object authorize(ProceedingJoinPoint joinPoint) throws Throwable {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserEntity> user = userRepository.findByUsername(authentication.getName());

        RequireRole auth = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(RequireRole.class);

        if (user.isPresent() &&
                Arrays.stream(auth.roles()).anyMatch(
                        role -> role.name().equals(user.get().getRole().getName().toString())
                )
        ) {
            return joinPoint.proceed();
        }

        throw new UnauthorizedException("Unauthorized");

    }

}
