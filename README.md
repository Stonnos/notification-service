Notification service v1.0
========================================

Описание
----------------------------------------
   Notification service представляет собой SOAP веб - сервис для отправки email.

Необходимый софт
----------------------------------------
* jdk 1.8
* maven >= 3.3.9
* База данных PostgreSQL для хранения информации.

Описание ключевой конфигурации модуля
----------------------------------------
Настройки для проекта находятся в файле application.yml. Ниже приведены основные параметры:
1) spring.datasource - настройки БД для хранения информации
2) webServiceConfig - настройки конфигурации веб - сервиса
    * webServiceConfig.wsdlConfig.portTypeName - список операций, которые могут быть выполнены с сообщениями
    * webServiceConfig.wsdlConfig.locationUri - url веб - сервиса
    * webServiceConfig.wsdlConfig.targetNamespace - целевое пространство имен схемы
    * webServiceConfig.wsdlConfig.xsdSchema: путь к xsd схеме
3) mailConfig - основные настройки модуля
    * maxFailedAttemptsToSent - максимальное число попыток для отправки email
    * pageSize - число писем для отправки за один раз
    * delaySeconds - интервал в сек. между отправками писем
    
Инструкция по развертыванию
----------------------------------------
       
1. Собрать проект с помощью системы сборки проекта maven. Ниже приведен пример команды:

   mvn clean install
   
2. Развернуть target/notification-service-1.0.war на одном из контейнеров сервлетов (например, Tomcat)
   с префиксом /notification-service.

   
