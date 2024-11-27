create table if not exists persons (
  id serial primary key,
  name varchar(30) not null,
  birthdate timestamp
);

create table if not exists genres (
  id serial primary key,
  name varchar(30) not null,
  description varchar(200)
);

create table if not exists films (
  id serial primary key,
  name varchar(30) not null,
  release_date timestamp not null,
  director_id bigint references persons(id) not null,
  description varchar(500)
);

create table if not exists films_genres (
  film_id bigint references films(id) on delete cascade not null,
  genre_id bigint references genres(id) on delete cascade not null,
  constraint uq_film_genre unique (film_id, genre_id)
);
