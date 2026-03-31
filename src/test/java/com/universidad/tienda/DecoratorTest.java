package com.universidad.tienda;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.universidad.tienda.decorator.AuditoriaDecorator;
import com.universidad.tienda.decorator.LoggingDecorator;
import com.universidad.tienda.decorator.OrdenServicio;
import com.universidad.tienda.decorator.OrdenServicioBase;
import com.universidad.tienda.decorator.ValidacionDecorator;
class DecoratorTest {
 private OrdenServicio buildCompleto() {
 OrdenServicio base = new OrdenServicioBase();
 return new AuditoriaDecorator(
 new ValidacionDecorator(
 new LoggingDecorator(base)));
 }
 @Test
 void testOrdenValida() {
 OrdenServicio svc = buildCompleto();
 String result = svc.procesarOrden("ORD-001", 50000.0);
 assertTrue(result.startsWith("PROCESADA:"));
 }
 @Test
 void testOrdenMontoInvalido() {
 OrdenServicio svc = buildCompleto();
 assertThrows(IllegalArgumentException.class,
 () -> svc.procesarOrden("ORD-002", 0.0));
 }
 @Test
 void testOrdenIdVacio() {
 OrdenServicio svc = buildCompleto();
 assertThrows(IllegalArgumentException.class,
 () -> svc.procesarOrden("", 10000.0));
 }
 @Test
 void testDecoradorIndividualLogging() {
 OrdenServicio base = new OrdenServicioBase();
 OrdenServicio conLog = new LoggingDecorator(base);
 // Solo logging, sin validación — monto 0 no lanza excepción
 assertDoesNotThrow(() -> conLog.procesarOrden("ORD-003", 0.0));
 }
}