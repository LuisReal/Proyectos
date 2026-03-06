class playlist:

    def __init__(self, id_usuario, id_cancion, nombre, artista, album, fecha, imagen, spotify, youtube, audio):
        self.id_usuario = id_usuario
        self.id_cancion = id_cancion
        self.nombre = nombre
        self.artista = artista
        self.album = album
        self.fecha = fecha
        self.imagen = imagen
        self.spotify = spotify
        self.youtube = youtube
        self.audio = audio

    def getIdUsuario(self):

        return self.id_usuario 
    
    def getId(self):

        return self.id_cancion 

    def getNombre(self):

        return self.nombre
    
    def getArtista(self):

        return self.artista

    def getAlbum(self):

        return self.album

    def getFecha(self):

        return self.fecha

    def getImagen(self):

        return self.imagen

    def getSpotify(self):

        return self.spotify

    def getYoutube(self):

        return self.youtube
    
    def getAudio(self):

        return self.audio

    def setIdUsuario(self, id_usuario):
        self.id_usuario = id_usuario

    def setId(self, id_cancion):
        self.id_cancion = id_cancion
    
    def setNombre(self, nombre):
        self.nombre = nombre
    
    def setArtista(self, artista):
        self.artista = artista

    def setAlbum(self, album):
        self.album = album
    
    def setFecha(self, fecha):
        self.fecha = fecha
        
    def setImagen(self, imagen):
        self.imagen = imagen
        
    def setSpotify(self, spotify):
        self.spotify = spotify

    def setYoutube(self, youtube):
        self.youtube = youtube

    def setAudio(self, audio):
        self.audio = audio