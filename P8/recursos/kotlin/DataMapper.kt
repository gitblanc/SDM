import com.example.p3recycler.Pelicula
import com.example.p3recycler.remote.data.MovieListResult
import com.example.p3recycler.remote.data.MovieResult


object DataMapper {
    val BASE_URL_IMG: String = "https://image.tmdb.org/t/p/"
    val IMG_W342: String = "w342"
    val IMG_ORIGINAL: String = "original"
    val BASE_URL_YOUTUBE: String = "https://youtu.be/"
    val SITE_YOUTUBE: String = "YouTube"

    fun convertToDomain(peliculas: MovieListResult): List<Pelicula> {
        return convertMovieListToDomain(peliculas.results)
    }

    /**
     * Convierte datos de cada pelicula de la API: MovieData
     * en datos del dominio: Pelicula
     * @param movieData lista de películas de la API
     * @return lista de peliculas del dominio
     *
     * id               <-- id
     * titulo           <-- title
     * argumento        <-- overview
     * categoria        <-- genreIds (es una lista de id de generos)
     * duracion
     * fecha            <-- releaseDate
     * urlCaratula      <-- posterPath, completamos la url
     * urlFondo         <-- backdropPath
     * urlTrailer
     */
    fun convertMovieListToDomain(movieData: List<MovieResult>): List<Pelicula> {
        val lpeliculas: MutableList<Pelicula> = mutableListOf()

        for (peliApi in movieData) {
            var urlCaratula = if (peliApi.posterPath == null) {
                ""
            } else {
                BASE_URL_IMG + IMG_W342 + peliApi.posterPath
            }
            var urlFondo = if (peliApi.backdropPath == null) {
                ""
            } else {
                BASE_URL_IMG + IMG_ORIGINAL + peliApi.backdropPath
            }

            lpeliculas.add(
                Pelicula(
                    peliApi.id,
                    peliApi.title,
                    peliApi.overview,
                    "",
                    0,
                    peliApi.releaseDate,
                    urlCaratula,
                    urlFondo,
                    ""
                )
            )
        }

        return lpeliculas
    }

}