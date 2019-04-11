Possible way of doing it:

- Import everything from npm (with open source license?)
- Assign npm usernames to open-registry users
- Only allow activation if npm username can publish module with specific
  contents (evidence of npm account)
- Registry to deny publishing if new package or versions are not signed with
  authed keybase account
