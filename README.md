# AyDS-TP

Arquitectura y Diseño de Sistemas  

## TP General

1 - Clean Code / SOLID:
- Analizar la clase SongRepository. Explicar qué principios SOLID no se cumplen y por qué. 
- Limpiar la clase SongRepository,implementar la solución de los problemas descritos anteriormente.

2 - Implementar los test que cubran los casos de la clase SongRepository.

3 - La arquitectura del proyecto, cumple con MVC? Justificar.


1)No cumple SRP ya que la clase realiza muchas funcionallidadess, las cuales ni siquiera estan divididas dentro de la misma clase en varias funciomnes
. No cumple D ya que depende de una clase concreta y no de una abstraccion
. No cumple OCP ya que para agregar nuevas fuentes de datos hay que modificar getSongByTerm, cuya logica no es extensible ya que esta hardcodeada


3) cumple con MVC ya que esta divido con una clara separacion de responsabilidades entre Modelo Vista y Controllador
Su flujo es Usuario - vista - controlador - modelo - controlador - vista