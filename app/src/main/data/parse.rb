# encoding: UTF-8

require 'csv'
require 'sqlite3'
require 'fileutils'

def main
  db_name='co.creativev.cwc2015.db'
  `rm #{db_name}`

  SQLite3::Database.new(db_name) do |db|
    table_sql = <<-SQL
    CREATE TABLE matches (
      _id INTEGER PRIMARY KEY AUTOINCREMENT,
      name TEXT,
      group_name TEXT,
      match_date DATETIME,
      team1 TEXT,
      team2 TEXT,
      venue TEXT,
      score1 TEXT,
      overs1 TEXT,
      score2 TEXT,
      overs2 TEXT,
      won TEXT,
      result TEXT
    );
    SQL
    db.query(table_sql).close
    stmt=db.prepare('insert into matches(name, group_name, match_date, team1, team2, venue) values(?,?,?,?,?,?)')
    CSV.foreach('matches.csv', :encoding => 'utf-8', :headers => true) do |r|
      date=DateTime.parse(r[2].strip) #2015-02-14T11:00:00+1300
      args=[r[0].strip, r[1].strip, date.new_offset(0).strftime('%Y-%m-%d %H:%M:%S'), r[3] ? r[3].strip : '', r[5] ? r[5].strip : '', "#{r[9].strip}, #{r[7].strip}, #{r[8].strip}"]
      stmt.execute(args)
      puts args.join(',')
    end
    stmt.close
  end
  `mv #{db_name} ../assets/`
end

main

