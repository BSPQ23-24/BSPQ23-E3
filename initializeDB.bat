@echo off
for %%G in (".\sql\*.sql") do (
  mysql -u spq -pspq logifyDB < "%%G"
)