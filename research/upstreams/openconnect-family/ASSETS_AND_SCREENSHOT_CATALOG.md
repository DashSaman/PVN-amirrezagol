# OpenConnect Family — Assets / Screenshot / Visual Reference Catalog

Research date: 2026-08-14

State: `IN-RESEARCH`; visual-reference inventory only. No third-party branding or imagery is approved for PVNetwork reuse by this file.

## Reference projects

1. OpenConnect GUI — canonical current project on GitLab; archived historical GitHub source remains useful for file-level asset inventory.
2. NetworkManager-openconnect — canonical GNOME GitLab; GitHub mirror pinned for source inspection.
3. OpenConnect core — protocol/library project; not a product-UI branding source.

## OpenConnect GUI historical asset inventory

Pinned historical source used for asset archaeology:

`openconnect/openconnect-gui@1b9bc0ae61496a871dbee955ec2443e46d411ed4`

Tree: `aa8bbc95f31bb84c034093e6d1f01c14805fb482`.

Observed visual/resource paths include:

### Application identity / OS resources

- `src/openconnect-gui.ico`
- `src/Resources/mono_lock.icns`
- `src/Resources/mono_lock.png`
- `src/Resources/openconnect-gui.plist.in`
- `src/openconnect-gui.qrc`
- Windows resource/manifest files.

### Connection-state visuals

- `src/images/network-connected.png`
- `src/images/network-disconnected.png`
- `src/images/network-wired.png`
- macOS-specific connected/disconnected images under `src/images/+mac/`
- traffic-light state images: green/yellow/red/off.

### Profile / action visuals

- `src/images/profiles.png`
- `src/images/text-new.png`
- `src/images/text-editor.png`
- `src/images/trashcan.png`
- `src/images/process-stop.png`
- `src/images/edit-find.png`

### Installer visuals

- `nsis/images/header-openconnect.bmp`
- `nsis/images/install-openconnect.bmp`

## UI resource files useful as screenshot substitutes

Where current screenshots are incomplete, the source-backed `.ui` files provide stronger menu/control evidence than guessing from images:

- `src/dialog/mainwindow.ui`
- `src/dialog/NewProfileDialog.ui`
- `src/dialog/editdialog.ui`
- `src/dialog/logdialog.ui`

They should be mapped screen-by-screen in the v2 `CLIENT_UI_AND_MENUS.md` expansion rather than copied as PVNetwork UI.

## NetworkManager-openconnect visual/resources evidence

The pinned GNOME tree contains:

- GTK3/GTK4 connection-dialog `.ui` resources;
- appdata metadata/visual references;
- broad gettext catalogs including Persian;
- desktop/plugin integration assets.

These are useful for studying GNOME conventions and localization terminology, not for cloning PVNetwork visuals.

## Reuse-rights classification

### Code/UI design

- OpenConnect GUI application is GPL-2.0-or-later: reference/research by default for a closed commercial PVNetwork product.
- NetworkManager-openconnect is path-level licensed; general plugin/UI code includes GPL-2+ material and some components have different terms. No blanket copy approval.

### Images/icons/branding

Do not assume an image is reusable merely because it is in a public repository. Before copying any image into PVNetwork, verify:

- exact file-level license/copyright;
- trademark/brand implications;
- whether attribution is required;
- whether the asset is project identity rather than generic artwork.

PVNetwork must use the owner-supplied PVNetwork logo and its own visual identity.

## What PVNetwork should learn visually

Reference concepts worth retaining without copying assets:

- one unmistakable connection state;
- fast access to connect/disconnect;
- shallow profile actions;
- visible diagnostics/log access;
- platform-appropriate tray/menu-bar state;
- certificate/trust prompts that explain the decision;
- clear disconnected/connecting/connected/error/reconnecting states.

## Screenshot evidence policy for future work

For every serious client/server panel in the v2 campaign:

1. record screenshot/official-doc URL and date;
2. record product/version/platform shown;
3. connect each visible screen/menu to source/route/component where possible;
4. store references/metadata by default, not copied images;
5. only mirror images after explicit file-level reuse-rights review;
6. distinguish historical screenshots from current release UI.

## Remaining gaps

- current OpenConnect GUI v1.6.2/current-main official screenshot inventory;
- source-to-screen comparison against current canonical GitLab tree;
- NetworkManager current GTK3/GTK4 screenshot catalog;
- file-level rights for any asset that might be useful as documentation illustration;
- accessibility/HiDPI/RTL visual verification on actual running clients.
