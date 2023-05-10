# StoreApp

- Aplicacion Android que consume la API creada en la asignatura de Acceso a Datos (AmazonAA)
- Para que la aplicacion funcione debes tener ejecutandose la API de AmazonAA con

### Como compilar la aplicacion
* Necesitas tener instalado y configurado el SDK de Android asi como Android Studio
* Importa la aplicacion desde GitHub clonandola o descargandote el fichero con el codigo fuente
* Importa la aplicacion AmazonAA desde GitHub clonandola o descargandote el fichero con el codigo fuente y ejecutala con SpringBoot
* Configura los TOKENS de MapBox para poder acceder a los mapas.
    * Añade al fichero gradle.properties la cadena MAPBOX_DIRECTIONS_TOKEN=MI_TOKEN donde MI_TOKEN es tu token privado.
    * Añade un fichero xml en la carpeta values con esta estructura `<?xml version="1.0" encoding="utf-8"?>  
      <resources><string name="mapbox_access_token" translatable="false">PUBLICTOKEN</string></resources>` y añade tu TOKEN public de MapBox

### Requisitos obligatorios
- [x] La aplicación deberá consumir la API diseñada y desarrollada en la asignatura de Acceso a Datos. Al menos 2 operaciones de cada uno de estos métodos: POST, GET, PUT y DELETE. Al menos una de ellas permitirá listar información
- [x] La comunicación con la API se llevará a cabo utilizando Retrofit
- [x] Se hará uso de mapas para mostrar información (si es necesario, habrá que añadir información geográfica a alguno de los recursos de la API diseñada)
- [x] La aplicación se diseñará utilizando el patrón MVP (Model View Presenter)
- [x] El usuario podrá almacenar como “favorito” aquella información de la API que le resulte de interés

### Requisitos opcionales
- [x] Utilizar la Directions API para guiar al usuario a alguna ubicación relevante en la aplicación
- [x] Utiliza la herramienta Git (y GitHub) durante todo el desarrollo de la aplicación. Utiliza el gestor de Issues para los problemas/fallos que vayan surgiendo
- [ ] Añadir un menú de preferencias con al menos 3 opciones que modifiquen el comportamiento de la aplicación. Este menú estará siempre disponible en el ActionBar
- [ ] Hacer uso de mapas en las pantalla donde se registre información. Para indicar información geográfica, por ejemplo
- [x] El usuario podrá hacer búsquedas para filtrar la información en los listados de datos
- [ ] Diseñar al menos 3 layouts para otras posiciones de la pantalla (portrait/ landscape)
- [x] Utilizar Material Design para personalizar el diseño de la aplicación
- [x] Utilizar las credenciales de usuario en la aplicación para comunicarse con la API (si ésta se encuentra securizada con token JWT)
