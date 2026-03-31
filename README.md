# Tienda Patrones Estructurales

Proyecto en **Spring Boot 3.2.0** que extiende la tienda virtual con los patrones **Decorator** y **Facade**, aplicando principios SOLID y demostrando el valor de la composición sobre la herencia.

---

## 📂 Estructura del Proyecto

 ```text
   
     tienda-patrones-estructurales/
     ├── pom.xml
     └── src/
         ├── main/java/com/universidad/tienda/
         │   ├── TiendaApp.java
         │   ├── adapter/...
         │   ├── composite/...
         │   ├── servicio/TiendaServicio.java
         │   ├── decorator/
         │   │   ├── OrdenServicio.java
         │   │   ├── OrdenServicioBase.java
         │   │   ├── OrdenServicioDecorator.java
         │   │   ├── LoggingDecorator.java
         │   │   ├── ValidacionDecorator.java
         │   │   └── AuditoriaDecorator.java
         │   └── facade/
         │       ├── EmailService.java
         │       ├── SMSService.java
         │       ├── PushService.java
         │       └── NotificacionFacade.java
         └── test/java/com/universidad/tienda/
             ├── TiendaServicioTest.java
             └── DecoratorTest.java
  ```

---


## 🎯 Patrón Decorator
 
El patrón Decorator se usa para añadir funcionalidades opcionales al servicio de órdenes sin modificar la clase base.
La cadena de decoradores se define en DecoratorConfig:
   ```bash
   @Bean("ordenCompleto")
   public OrdenServicio ordenServicioCompleto(@Qualifier("ordenBase") OrdenServicio base) {
    return new AuditoriaDecorator(
        new ValidacionDecorator(
            new LoggingDecorator(base)
        )
    );
   }
   ```
---

## 🔗 Composición de la cadena
Pseudocódigo del flujo:
   ```bash
   OrdenServicioCompleto = Auditoria(
                           Validación(
                               Logging(
                                   Base
                               )
                           )
                       )
   ```

Orden de ejecución:

1. Auditoría registra la operación.
2. Validación verifica ID y monto.
3. Logging mide tiempo y registra inicio/fin.
4. Base procesa la orden.
5. Esto demuestra cómo la composición permite añadir capas sin alterar OrdenServicioBase.

---

## 📡 Patrón Facade
El subsistema de notificaciones tiene tres servicios: EmailService, SMSService y PushService.
En lugar de que el cliente los invoque directamente, se encapsulan en NotificacionFacade:
 ```bash
@Service
public class NotificacionFacade {
    private final EmailService email;
    private final SMSService sms;
    private final PushService push;

    public void notificarCompraExitosa(String correo, String telefono, String token, String ordenId) {
        String asunto = "Orden " + ordenId + " confirmada";
        String msg = "Su orden " + ordenId + " ha sido procesada exitosamente.";
        email.enviar(correo, asunto, msg);
        sms.enviar(telefono, msg);
        push.enviar(token, asunto, msg);
    }
}

 ```

---

## ✅ Justificación del uso de Facade
1. Simplificación: el cliente (controlador o servicio) hace una sola llamada en lugar de coordinar tres servicios.
2. Desacoplamiento: los detalles de cada canal (email, SMS, push) quedan ocultos.
3. Mantenibilidad: si cambia la implementación de un canal, el cliente no se ve afectado.
4. Consistencia: asegura que todos los canales se notifiquen con el mismo mensaje.

---

## 🧪 Ejecución de Pruebas
Tests de Decorator (DecoratorTest) validan:

1. Orden válida procesada correctamente.
2. Excepciones en monto fuera de rango o ID vacío.
3. Decoradores individuales funcionan de forma aislada.

Ejecutar:
```bash
mvn test
```
Resultado: 
<img width="863" height="225" alt="image" src="https://github.com/user-attachments/assets/e7560677-fe67-4676-aaa7-c69b75992c9a" />
