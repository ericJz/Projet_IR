# ne pas oublier de changer le port (ici 1099) si le serveur change de port
echo "Quel port souhaitez vous utiliser ?"
read port
echo "Le port "$port" est utilisé pour le service de nommage"
cd rmiregistry/;rmiregistry $port;