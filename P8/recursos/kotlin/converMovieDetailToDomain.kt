    /**
     * Partiendo de un objeto pelicula ya existente
     * Convierte datos de detalle de la película al dominio
     * y completa el objeto pelicula
     * categoria        <-- primer género que encontramos (si no hay -> vacío)
     * duracion         <-- conversión desde minutos(int) -> formato 1h 23m
     * urlTrailer       <-- url trailer en formato youtu.be
     *
     * @param data
     * @param pelicula
     */
    fun convertMovieDetailToDomain(data: MovieDetail): Pelicula {
        // con los detalles del servicio vamos a rellenar los datos que nos faltan
        // en pelicula

        var urlCaratula = if (data.posterPath == null) {
            ""
        } else {
            BASE_URL_IMG + IMG_W342 + data.posterPath
        }
        var urlFondo = if (data.backdropPath == null) {
            ""
        } else {
            BASE_URL_IMG + IMG_ORIGINAL + data.backdropPath
        }

        var categoria= if (data.genres.size > 0) {
            data.genres[0].name
        } else {
            "?"
        }

        var youTubeUrl = ""
        // comprueba si hay al menos un vídeo asociado
        if (data.videos.results.size > 0) // Si el primer vídeo es de youtube obtiene el código y crea con el la url
            if (data.videos.results[0].site == SITE_YOUTUBE) youTubeUrl =
                BASE_URL_YOUTUBE + data.videos.results[0].key
        Log.d("ShowMovie", "youTubeUrl= $youTubeUrl")

        val peliculaCompleta = Pelicula(
            id = data.id,
            titulo = data.title,
            argumento = data.overview,
            categoria = categoria,
            duracion = data.runtime,
            fecha = data.releaseDate,
            urlCaratula = urlCaratula,
            urlFondo = urlFondo,
            urlTrailer = youTubeUrl
        )

        return peliculaCompleta
    }