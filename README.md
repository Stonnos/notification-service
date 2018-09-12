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
   
2. Развернуть target/notification-service.war на одном из контейнеров сервлетов (например, Tomcat)
   с префиксом /notification-service.
   
Инструкция по развертыванию в Docker
-------------------------------------------------------

1. Для Windows достаточно скачать и установить дистрибутив Docker Desktop (https://www.docker.com/products/docker-desktop).

2. Далее для сборки проекта и создания образа проекта нужно выполнить команду

mvn clean install dockerfile:build

3. Используя пакетный менеджер docker-compose, создать docker контейнеры с помощью команды:

docker-compose up

ВАЖНО! Данную команду необходимо выполнять из корневой папки проекта.

Для развертывания окружения на production, в файле application.yml необходимо установить следующие параметры:

  * spring.datasource.url=jdbc:postgresql://notification-db:5432/mail

   
