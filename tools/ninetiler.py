import pygame, json

pygame.init()

TILESIZE = 16
SCALE = 8

MODE_LOADING = 0
MODE_IMAGE = 1
MODE_NINETILE = 2
mode = MODE_LOADING

numberOfTiles = 85
imagename = "TILE_marrow.png"
filename = "tools\\marrow.json"

running = True

tiles = []
parts = 0

def makeNewNineTile(partsIN, imagepath, jsonpath):
    global parts, screen, image, outpath
    parts = partsIN
    for i in range(parts):
        tiles.append([0, 0, 0])
    image = pygame.transform.scale_by(pygame.image.load("res\\tile\\" + imagepath), SCALE)
    screen = pygame.display.set_mode((TILESIZE*SCALE*5, TILESIZE*SCALE*5))
    outpath = jsonpath

data = []

def interpretTilesList(tiles):
    outlist = []
    for i in range(9):
        partlist = []
        for tile in tiles:
            if tile[2] == i+1:
                partlist.append(tile[:2])
        outlist.append(partlist)
    return outlist

def makeModeNineTile():
    global interpretedTiles, mode
    interpretedTiles = interpretTilesList(tiles)
    print(nineTileLayouts)
    mode = MODE_NINETILE
def saveTiles():
    with open(filename, mode="w+") as f:
        json.dump([tiles[:numberOfTiles], nineTileLayouts], f)

interpretedTiles = []
nineTileLayouts = []
for i in range(256):
    nineTileLayouts.append([0, 0, 0, 0, 0, 0, 0, 0, 0])


try:
    with open(filename) as f:
        data = json.load(f)
        image = pygame.transform.scale_by(pygame.image.load("res\\tile\\" + imagename), SCALE)
        screen = pygame.display.set_mode((TILESIZE*SCALE*5, TILESIZE*SCALE*5))
        tiles = data[0] 
        if len(data[1]) == 256:
            nineTileLayouts = data[1]
        # print(tiles)
        parts = numberOfTiles
except:
    makeNewNineTile(numberOfTiles, imagename, filename)


mode = MODE_IMAGE

currentpart = 0
for i in range(parts):
    if tiles[i][0] == 0 and tiles[i][1] == 0 and tiles[i][2] == 0:
        currentpart = i
        break
if currentpart == 0:
    print("mode:ninetile")
    makeModeNineTile()

currentx = tiles[currentpart][0]
currenty = tiles[currentpart][1]

currentNineTile = 0


tileselectrect = pygame.rect.Rect(2*TILESIZE*SCALE, 2*TILESIZE*SCALE, TILESIZE*SCALE, TILESIZE*SCALE)
nineTileRect = pygame.rect.Rect(1*TILESIZE*SCALE, 1*TILESIZE*SCALE, 3*TILESIZE*SCALE, 3*TILESIZE*SCALE)
font = pygame.font.Font(None, 8*SCALE)

while running:
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            running = False
        if event.type == pygame.KEYDOWN:
            if event.key == pygame.K_MINUS:
                mode = MODE_IMAGE
                currentpart = 0
            if event.key == pygame.K_EQUALS:
                makeModeNineTile()
                
            if mode == MODE_IMAGE:
                if event.key == pygame.K_RIGHT:
                    currentx += 1
                    tiles[currentpart][0] = currentx
                if event.key == pygame.K_LEFT:
                    currentx -= 1
                    tiles[currentpart][0] = currentx
                if event.key == pygame.K_DOWN:
                    currenty += 1
                    tiles[currentpart][1] = currenty
                if event.key == pygame.K_UP:
                    currenty -= 1
                    tiles[currentpart][1] = currenty
                if event.key == pygame.K_1:
                    tiles[currentpart][2] = 1
                if event.key == pygame.K_2:
                    tiles[currentpart][2] = 2
                if event.key == pygame.K_3:
                    tiles[currentpart][2] = 3
                if event.key == pygame.K_4:
                    tiles[currentpart][2] = 4
                if event.key == pygame.K_5:
                    tiles[currentpart][2] = 5
                if event.key == pygame.K_6:
                    tiles[currentpart][2] = 6
                if event.key == pygame.K_7:
                    tiles[currentpart][2] = 7
                if event.key == pygame.K_8:
                    tiles[currentpart][2] = 8
                if event.key == pygame.K_9:
                    tiles[currentpart][2] = 9
                if event.key == pygame.K_SPACE:
                    if pygame.key.get_mods() & pygame.KMOD_SHIFT:
                        currentpart = max(0, currentpart-1)
                        if currentpart >= 0:
                            currentx = tiles[currentpart][0]
                            currenty = tiles[currentpart][1]
                    else:
                        currentpart += 1
                        if currentpart < parts:
                            currentx = tiles[currentpart][0]
                            currenty = tiles[currentpart][1]

                    if currentpart >= parts:
                        makeModeNineTile()
                    
                    saveTiles()

            if mode == MODE_NINETILE:
                saveTiles()
                if event.key == pygame.K_SPACE:
                    if pygame.key.get_mods() & pygame.KMOD_SHIFT:
                        currentNineTile = (currentNineTile+255)%256
                    else:
                        currentNineTile = (currentNineTile+1)%256

                if event.key == pygame.K_1:
                    if pygame.key.get_mods() & pygame.KMOD_SHIFT:
                        nineTileLayouts[currentNineTile][0] = (nineTileLayouts[currentNineTile][0] - 1) % len(interpretedTiles[0])
                    else:
                        nineTileLayouts[currentNineTile][0] = (nineTileLayouts[currentNineTile][0] + 1) % len(interpretedTiles[0])
                if event.key == pygame.K_2:
                    if pygame.key.get_mods() & pygame.KMOD_SHIFT:
                        nineTileLayouts[currentNineTile][1] = (nineTileLayouts[currentNineTile][1] - 1) % len(interpretedTiles[1])
                    else:
                        nineTileLayouts[currentNineTile][1] = (nineTileLayouts[currentNineTile][1] + 1) % len(interpretedTiles[1])
                if event.key == pygame.K_3:
                    if pygame.key.get_mods() & pygame.KMOD_SHIFT:
                        nineTileLayouts[currentNineTile][2] = (nineTileLayouts[currentNineTile][2] - 1) % len(interpretedTiles[2])
                    else:
                        nineTileLayouts[currentNineTile][2] = (nineTileLayouts[currentNineTile][2] + 1) % len(interpretedTiles[2])
                if event.key == pygame.K_4:
                    if pygame.key.get_mods() & pygame.KMOD_SHIFT:
                        nineTileLayouts[currentNineTile][3] = (nineTileLayouts[currentNineTile][3] - 1) % len(interpretedTiles[3])
                    else:
                        nineTileLayouts[currentNineTile][3] = (nineTileLayouts[currentNineTile][3] + 1) % len(interpretedTiles[3])
                if event.key == pygame.K_5:
                    if pygame.key.get_mods() & pygame.KMOD_SHIFT:
                        nineTileLayouts[currentNineTile][4] = (nineTileLayouts[currentNineTile][4] - 1) % len(interpretedTiles[4])
                    else:
                        nineTileLayouts[currentNineTile][4] = (nineTileLayouts[currentNineTile][4] + 1) % len(interpretedTiles[4])
                if event.key == pygame.K_6:
                    if pygame.key.get_mods() & pygame.KMOD_SHIFT:
                        nineTileLayouts[currentNineTile][5] = (nineTileLayouts[currentNineTile][5] - 1) % len(interpretedTiles[5])
                    else:
                        nineTileLayouts[currentNineTile][5] = (nineTileLayouts[currentNineTile][5] + 1) % len(interpretedTiles[5])
                if event.key == pygame.K_7:
                    if pygame.key.get_mods() & pygame.KMOD_SHIFT:
                        nineTileLayouts[currentNineTile][6] = (nineTileLayouts[currentNineTile][6] - 1) % len(interpretedTiles[6])
                    else:
                        nineTileLayouts[currentNineTile][6] = (nineTileLayouts[currentNineTile][6] + 1) % len(interpretedTiles[6])
                if event.key == pygame.K_8:
                    if pygame.key.get_mods() & pygame.KMOD_SHIFT:
                        nineTileLayouts[currentNineTile][7] = (nineTileLayouts[currentNineTile][7] - 1) % len(interpretedTiles[7])
                    else:
                        nineTileLayouts[currentNineTile][7] = (nineTileLayouts[currentNineTile][7] + 1) % len(interpretedTiles[7])
                if event.key == pygame.K_9:
                    if pygame.key.get_mods() & pygame.KMOD_SHIFT:
                        nineTileLayouts[currentNineTile][8] = (nineTileLayouts[currentNineTile][8] - 1) % len(interpretedTiles[8])
                    else:
                        nineTileLayouts[currentNineTile][8] = (nineTileLayouts[currentNineTile][8] + 1) % len(interpretedTiles[8])
                


    screen.fill("black")
    if mode == MODE_IMAGE:
        screen.blit(image, ((2-currentx)*TILESIZE*SCALE, (2-currenty)*TILESIZE*SCALE))
        pygame.draw.rect(screen, "yellow", tileselectrect, 4)
        screen.blit(font.render(f"{currentpart}: {currentx}, {currenty} | {tiles[currentpart%numberOfTiles][2]}", True, "green"), (0, 0))

    if mode == MODE_NINETILE:
        screen.fill("grey", nineTileRect)
        if ((currentNineTile >> 0 & 1) == 1):
            screen.fill("white", pygame.rect.Rect(TILESIZE*SCALE, TILESIZE*SCALE, TILESIZE*SCALE, TILESIZE*SCALE))
        if ((currentNineTile >> 1 & 1) == 1):
            screen.fill("white", pygame.rect.Rect(2*TILESIZE*SCALE, TILESIZE*SCALE, TILESIZE*SCALE, TILESIZE*SCALE))
        if ((currentNineTile >> 2 & 1) == 1):
            screen.fill("white", pygame.rect.Rect(3*TILESIZE*SCALE, TILESIZE*SCALE, TILESIZE*SCALE, TILESIZE*SCALE))
        if ((currentNineTile >> 3 & 1) == 1):
            screen.fill("white", pygame.rect.Rect(TILESIZE*SCALE, 2*TILESIZE*SCALE, TILESIZE*SCALE, TILESIZE*SCALE))
        if ((currentNineTile >> 4 & 1) == 1):
            screen.fill("white", pygame.rect.Rect(3*TILESIZE*SCALE, 2*TILESIZE*SCALE, TILESIZE*SCALE, TILESIZE*SCALE))
        if ((currentNineTile >> 5 & 1) == 1):
            screen.fill("white", pygame.rect.Rect(TILESIZE*SCALE, 3*TILESIZE*SCALE, TILESIZE*SCALE, TILESIZE*SCALE))
        if ((currentNineTile >> 6 & 1) == 1):
            screen.fill("white", pygame.rect.Rect(2*TILESIZE*SCALE, 3*TILESIZE*SCALE, TILESIZE*SCALE, TILESIZE*SCALE))
        if ((currentNineTile >> 7 & 1) == 1):
            screen.fill("white", pygame.rect.Rect(3*TILESIZE*SCALE, 3*TILESIZE*SCALE, TILESIZE*SCALE, TILESIZE*SCALE))
        screen.blit(font.render(f"{currentNineTile} | {str(nineTileLayouts[currentNineTile])}", True, "green"), (0, 0))

        for i in range(9):
            # print()
            # print(nineTileLayouts[currentNineTile][i])
            # print(i)
            # print(interpretedTiles[i][nineTileLayouts[currentNineTile][i]])
            screen.blit(image.subsurface(TILESIZE*SCALE*interpretedTiles[i][nineTileLayouts[currentNineTile][i]][0],
                                        TILESIZE*SCALE*interpretedTiles[i][nineTileLayouts[currentNineTile][i]][1], 
                                        TILESIZE*SCALE, TILESIZE*SCALE), 
                                        (2*TILESIZE*SCALE, 2*TILESIZE*SCALE))

    screen.blit(font.render(f"{len(tiles)}", True, "green"), (0, TILESIZE*SCALE*4))
    screen.blit(font.render(f"{mode}", True, "green"), (0, TILESIZE*SCALE*4.5))
        
    pygame.display.flip()

pygame.quit()