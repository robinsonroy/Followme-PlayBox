# Followme-PlayBox
PlayBox's java of FollowMe system base on music player deamon


This programme need to mpd and mpc install on your raspberry B+ (PlayBox)
1) install mpd :
# apt-get install mpd mpc

2) configure file for local music :
In /etc/mpd.conf change this line with the good music directory

music_directory                 "<music directory>"

Check mpd have good right to read <music directory>

3) create mpd database

# mpd --create-db

4) start mpd

# /etc/init.d/mpd start

5) update mpc

# mpc update

For more information on mpd :
http://mpd.wikia.com/wiki/Install


