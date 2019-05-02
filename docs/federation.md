## Federation

Federation in Open-Registry brings you the ability of caching packages locally
on the fly, so the rest of your room-mates, house, office, coop or anything really,
can share the packages in-between you instead of downloading them from the internet
all the time.

For you as a user, these are the following benefits:

- Faster performance (if packages exists closer than the centralized index)
- On-demand offline cache of packages and metadata
- Ability to setup local on-premise mirrors without having to download the full registry
- Lower bandwidth as libp2p will use local connections if possible

## Getting Started

Getting started with federation is easy!

Notice: by default, starting a federation node will automatically start sharing
downloaded packages with others. Don't worry, only packages existing in the
public index is shared, not your local or otherwise private packages

1. Download `Open-Registry-Fed` for your operating system

2. Run it!

3. Run `npm config set registry http://localhost:4324` to point your client to
   your newly created local registry

4. Now run `npm install` in your project! All packages downloaded via your local
   registry is cached forever so you can run it offline and shared with others
   who also use the federated registry
