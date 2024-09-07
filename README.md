# Proyecto Gestión de recursos educativos
Integrantes: Amaro Fibla - Andrés García - Marcelo Morales
## Descripción del proyecto
Este proyecto consta de una gestion de recursos educativos, estilo "Aula Virtual", otorgando un acceso a profesores o encargados de curso (Ayudante, tutores, etc.) para agregar recursos educativos y a los estudiantes asignados a dicho curso, el poder acceder a dichos recursos e interactuar con ellos.
## Funciones Presentes
Dentro de nuestro proyecto se puede interactuar directamente con los recursos, esto dependiendo de la calidad de usuario que poseas, si eres "Profesor o encargado de curso" o si eres un alumno.

Para cada profesor, se puede ingresar a los cursos los cuales dictas y una vez este en el curso deseado, este puede agregar, eliminar o modificar los recursos dentro de ese curso.

Por otro lado, si eres alumno, puedes tener acceso a todos los cursos los cuales estas cursando, teniendo la capacidad de interactuar con todos los recursos que el Profesor o encargado de curso ponga a tu disposición.

## Estructura de datos / JavaColeccions presentes
Para organizar nuestro datos y poder obtener la mayor eficiencia del manejo de recursos, hemos utilizado e implementado un "Hash Map" para cada recurso a agregar, eliminar o modificar.
Dicho mapa se encuentra dentro de una "List" que almacena carpetas, dentro de la clase "Curso" esto nos permite que dentro de cada curso, podamos organizar varias carpetas en las cuales la presencia del mapa nos ayude a hacer una busqueda mucho mas efectiva al recurso que queremos acceder.

### Cabe recalcar que esto es un demo, un avance del proyecto, por lo cual iremos agregando mas funciones con el paso del tiempo
