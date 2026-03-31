package com.universidad.tienda;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.universidad.tienda.decorator.AuditoriaDecorator;
import com.universidad.tienda.decorator.LoggingDecorator;
import com.universidad.tienda.decorator.OrdenServicio;
import com.universidad.tienda.decorator.ValidacionDecorator;
@Configuration
public class DecoratorConfig {
 // Cadena: Auditoria → Validacion → Logging → Base
 @Bean("ordenCompleto")
 public OrdenServicio ordenServicioCompleto(
 @Qualifier("ordenBase") OrdenServicio base) {
 return new AuditoriaDecorator(
 new ValidacionDecorator(
 new LoggingDecorator(base)));
 }
}
